package com.oversea.shipping.service;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.oversea.shipping.dao.ShipmentRepository;
import com.oversea.shipping.model.Customer;
import com.oversea.shipping.model.PackageStatus;
import com.oversea.shipping.model.ShipDate;
import com.oversea.shipping.model.Shipment;

@Service
public class ShipmentServiceImpl implements ShipmentService {

	@Autowired
	private ShipmentRepository ShipmentRepository;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ShipDateService shipDateService;

	Logger logger = LoggerFactory.getLogger(ShipmentServiceImpl.class);

	public List<Shipment> findAll() {
		return ShipmentRepository.findAllByOrderByCreateDateDesc();
	}

	@Override
	public List<Shipment> findByCustomer(Customer customer) {
		return ShipmentRepository.findByCustomer(customer);
	}

	public Shipment findByTrackingNumber(String trackingNumber) {
		Shipment theShipment = ShipmentRepository.findByTrackingNumber(trackingNumber);

		if (theShipment == null) {
			// we didn't find the Shipment
			throw new RuntimeException("Did not find tracking Number - " + trackingNumber);
		}

		return theShipment;
	}

	@Transactional 
	public void save(Shipment theShipment) {

		double unit = theShipment.getLength() * theShipment.getWidth() * theShipment.getHeight() / 6000;

		if (unit < theShipment.getWeight()) {
			unit = theShipment.getWeight();
		}

		DecimalFormat format = new DecimalFormat("###.##");
		unit = Double.valueOf(format.format(unit));

		if (theShipment.getUnit() == 0) {
			theShipment.setUnit(unit);
		}
		
		if(theShipment.getUnit_price() == 0) {
			ShipDate shipDate = shipDateService.findById(theShipment.getShipDate().getId()); 
			theShipment.setUnit_price(shipDate.getUnitPrice());
		}

		theShipment.setShipping_price(theShipment.getUnit() * theShipment.getUnit_price());

		if (theShipment.getStatus() == null) {
			theShipment.addPackageStatus(PackageStatus.NEW);
		}

		if (theShipment.getDeliveryMethod() == null) {
			theShipment.setDeliveryMethod("PickUp");
		}

		if (unit > 0 && theShipment.getStatus().equals(PackageStatus.NEW)) {
			theShipment.addPackageStatus(PackageStatus.RECEIVED);
		}

		ShipmentRepository.save(theShipment);
	}

	@Transactional
	public void deleteByTrackingNumber(String trackingNumber) {
		ShipmentRepository.deleteByTrackingNumber(trackingNumber);
	}

	@Override
	public void updatePackageStatus(Shipment theshipment) {
		PackageStatus status = theshipment.getStatus();

		switch (status) {
		case NEW:
			theshipment.addPackageStatus(PackageStatus.RECEIVED);
			break;
		case RECEIVED:
			theshipment.addPackageStatus(PackageStatus.UNPAY);
			break;
		case UNPAY:
			theshipment.addPackageStatus(PackageStatus.SHIPPING);
			break;
		case SHIPPING:
			theshipment.addPackageStatus(PackageStatus.ARRIVED);
			break;
		case ARRIVED:
			if ("Delivery".equals(theshipment.getDeliveryMethod())) {
				theshipment.addPackageStatus(PackageStatus.DELIVERY);
			} else {
				theshipment.addPackageStatus(PackageStatus.PICKUP);
			}
			break;
		case PICKUP:
			theshipment.addPackageStatus(PackageStatus.PICKEDUP);
			break;
		case DELIVERY:
			theshipment.addPackageStatus(PackageStatus.DELIVERIED);
			break;
		default:
			break;
		}
		
		ShipmentRepository.save(theshipment);
	}

	@Override
	public List<Shipment> findByShipDate(ShipDate shipDate) {
		return ShipmentRepository.findByShipDate(shipDate);
	}

	@Override
	public void updatePackageStatus(Shipment theshipment, PackageStatus status) {
		theshipment.addPackageStatus(status);
		ShipmentRepository.save(theshipment);
	}

	@SuppressWarnings({ "resource" })
	@Override
	public void uploadShipmentFromExcel(InputStream input) throws Exception {
		Map<String, Integer> header = new HashMap<String, Integer>();
		header.put("ShipDate", -1);
		header.put("Email", -1);
		header.put("WechatId", -1);
		header.put("TrackingNumber", -1);
		header.put("ShipingCompany", -1);
		header.put("Weight", -1);
		header.put("Height", -1);
		header.put("Length", -1);
		header.put("Width", -1);
		header.put("PackageValue", -1);
		header.put("Description", -1);
		header.put("CreateDate", -1);
		header.put("Note", -1);

		Workbook workbook = new XSSFWorkbook(input);
		Sheet sheet = workbook.getSheetAt(0);

		Iterator<Row> rows = sheet.iterator();

		if (rows.hasNext()) {
			Row row = rows.next();
			// For each row, iterate through all the columns
			Iterator<Cell> cellIterator = row.cellIterator();
			int colNum = 0;
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String headerText = cell.getStringCellValue();
				if (header.containsKey(headerText)) {
					header.put(headerText, colNum);
				}
				colNum++;
			}
		}

		// verify header
		for (Map.Entry<String, Integer> entry : header.entrySet()) {
			if (entry.getValue() < 0)
				throw new Exception(entry.getKey() + " is missing from Excel");
		}

		while (rows.hasNext()) {
			Row currentRow = rows.next();

			String trackingNumber = currentRow.getCell(header.get("TrackingNumber")) != null? currentRow.getCell(header.get("TrackingNumber")).getStringCellValue():null;
			String wechatId = currentRow.getCell(header.get("WechatId")) != null? currentRow.getCell(header.get("WechatId")).getStringCellValue():null;
			String shippingDate = currentRow.getCell(header.get("ShipDate")) != null? currentRow.getCell(header.get("ShipDate")).getStringCellValue():null;
			ShipDate shipDate = shipDateService.findByShippingDate(shippingDate);

			logger.info("import trackingNumber: " + trackingNumber);

			if (shipDate == null)
				throw new Exception(shippingDate + " is not a valid shipping date");

			if (!StringUtils.isEmpty(trackingNumber) && !StringUtils.isEmpty(wechatId)) {
				Shipment shipment = ShipmentRepository.findByTrackingNumber(trackingNumber);
				if (shipment == null) {
					shipment = new Shipment();

					Customer customer = customerService.findByWeChatId(wechatId);
					if (customer == null) {
						customer = new Customer();
						customer.setWechatId(wechatId);
						customer.setEmail(currentRow.getCell(header.get("Email")).getStringCellValue());
						customerService.save(customer);
					}

					shipment.setTrackingNumber(trackingNumber);
					shipment.setShipingCompany(currentRow.getCell(header.get("ShipingCompany")) != null
							? currentRow.getCell(header.get("ShipingCompany")).getStringCellValue()
							: null);
					shipment.setWeight(currentRow.getCell(header.get("Weight")) != null
							? currentRow.getCell(header.get("Weight")).getNumericCellValue()
							: 0);
					shipment.setHeight(currentRow.getCell(header.get("Height")) != null
							? currentRow.getCell(header.get("Height")).getNumericCellValue()
							: 0);
					shipment.setLength(currentRow.getCell(header.get("Length")) != null
							? currentRow.getCell(header.get("Length")).getNumericCellValue()
							: 0);
					shipment.setWidth(currentRow.getCell(header.get("Width")) != null
							? currentRow.getCell(header.get("Width")).getNumericCellValue()
							: 0);
					shipment.setPackageValue(currentRow.getCell(header.get("PackageValue")) != null
							? currentRow.getCell(header.get("PackageValue")).getNumericCellValue()
							: 0);
					shipment.setDescription(currentRow.getCell(header.get("Description")) != null
							? currentRow.getCell(header.get("Description")).getStringCellValue()
							: null);
					shipment.setNote(currentRow.getCell(header.get("Note")) != null
							? currentRow.getCell(header.get("Note")).getStringCellValue()
							: null);
					shipment.setCreateDate(currentRow.getCell(header.get("CreateDate")) != null
							? currentRow.getCell(header.get("CreateDate")).getDateCellValue()
							: new Date());
					shipment.setShipDate(shipDate);
					shipment.setCustomer(customer);

					this.save(shipment);
				}else {
					logger.info("trackingNumber: " + trackingNumber + " exist, skip import");
				}
			}
		}

		workbook.close();
	}

	@Override
	public List<Shipment> findByShipDateAndStatus(ShipDate shipDate, PackageStatus packageStatus) {
		return ShipmentRepository.findByShipDateAndStatus(shipDate, packageStatus);
	}

}
