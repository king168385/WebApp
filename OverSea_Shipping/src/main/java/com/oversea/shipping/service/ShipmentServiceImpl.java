package com.oversea.shipping.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
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
		return ShipmentRepository.findByTrackingNumber(trackingNumber);
	}

	@Transactional
	public void save(Shipment theShipment) {

		double unit = theShipment.getLength() * theShipment.getWidth() * theShipment.getHeight() / 6000;

		if (unit < theShipment.getWeight()) {
			unit = theShipment.getWeight();
		}

		DecimalFormat format = new DecimalFormat("###.##");
		unit =Double.valueOf(format.format(unit));

		if (theShipment.getUnit() == 0) {
			theShipment.setUnit((int) Math.floor(unit+0.6));
		}

		if (theShipment.getUnit_price() == 0) {
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
		header.put("Dimension", -1);
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

		int rowNum = 1;
		while (rows.hasNext()) {
			Row currentRow = rows.next();

			String trackingNumber = currentRow.getCell(header.get("TrackingNumber")) != null
					? currentRow.getCell(header.get("TrackingNumber")).getStringCellValue()
					: null;
			String wechatId = currentRow.getCell(header.get("WechatId")) != null
					? currentRow.getCell(header.get("WechatId")).getStringCellValue()
					: null;
			String shippingDate = currentRow.getCell(header.get("ShipDate")) != null
					? currentRow.getCell(header.get("ShipDate")).getStringCellValue()
					: null;
			ShipDate shipDate = shipDateService.findByShippingDate(shippingDate);

			logger.info("import trackingNumber: " + trackingNumber);

			if (shipDate == null) {
				throw new Exception("Line " + rowNum + ": shipping date " + shippingDate + " is not valid.");
			}

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

					if (currentRow.getCell(header.get("Dimension")) != null) {
						String dimension = currentRow.getCell(header.get("Dimension")).getStringCellValue();
						String[] dimensions = dimension.split("*");
						if (dimensions.length == 3) {
							shipment.setLength(Double.valueOf(dimensions[0]));
							shipment.setWidth(Double.valueOf(dimensions[1]));
							shipment.setHeight(Double.valueOf(dimensions[2]));
						}

					}

					shipment.setShipDate(shipDate);
					shipment.setCustomer(customer);

					this.save(shipment);
				} else {
					boolean updateShipment = false;
					if (shipment.getUnit() == 0) {
						if (currentRow.getCell(header.get("Weight")) != null) {
							shipment.setWeight(currentRow.getCell(header.get("Weight")).getNumericCellValue());
							updateShipment = true;
						}
						if (currentRow.getCell(header.get("Dimension")) != null) {
							String dimension = currentRow.getCell(header.get("Dimension")).getStringCellValue();
							String[] dimensions = dimension.trim().split("\\*");
							if (dimensions.length == 3) {
								shipment.setLength(Double.valueOf(dimensions[0]));
								shipment.setWidth(Double.valueOf(dimensions[1]));
								shipment.setHeight(Double.valueOf(dimensions[2]));
								updateShipment = true;
							}
						}

						if (updateShipment) {
							logger.info("trackingNumber: " + trackingNumber + " exist, update dimension");
							this.save(shipment);
						} else {
							logger.info("trackingNumber: " + trackingNumber + " exist, skip import");
						}
					} else {
						logger.info("trackingNumber: " + trackingNumber + " exist, skip import");
					}
				}
			}
			rowNum++;
		}

		workbook.close();
	}

	@Override
	public List<Shipment> findByShipDateAndStatus(ShipDate shipDate, PackageStatus packageStatus) {
		return ShipmentRepository.findByShipDateAndStatus(shipDate, packageStatus);
	}

	@SuppressWarnings("resource")
	@Override
	public ByteArrayInputStream exportToExcel(List<Shipment> theshipments) throws Exception {
		Map<String, Integer> header = new HashMap<String, Integer>();
		header.put("ShipDate", 0);
		header.put("Email", 1);
		header.put("WechatId", 2);
		header.put("ShipingCompany", 3);
		header.put("TrackingNumber", 4);
		header.put("Description", 5);
		header.put("PackageValue", 6);
		header.put("Weight", 7);
		header.put("Dimension", 8);
		header.put("Unit", 9);
		header.put("Unit_Price", 10);
		header.put("Shipping_Price", 11);
		header.put("Status", 12);
		header.put("CreateDate", 13);
		header.put("Note", 14);
		header.put("DeliveryMethod", 15);
		header.put("PickupLocation", 16);
		header.put("DeliveryAddress", 17);
		header.put("DeliveryCity", 18);
		header.put("DeliveryProvince", 19);
		header.put("DeliveryPostCode", 20);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("shipments");

		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setColor(IndexedColors.BLACK.getIndex());
		
		CellStyle dateCellStyle = workbook.createCellStyle();
		CreationHelper createHelper = workbook.getCreationHelper();
		dateCellStyle.setDataFormat(
		    createHelper.createDataFormat().getFormat("m/d/yy h:mm"));

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Row for Header
		Row headerRow = sheet.createRow(0);
		
		for (Map.Entry<String, Integer> entry : header.entrySet()) {
			 Cell cell = headerRow.createCell(entry.getValue());
		        cell.setCellValue(entry.getKey());
		        cell.setCellStyle(headerCellStyle);
		}

		int rowIdx = 1;
	      for (Shipment shipment : theshipments) {
	        Row row = sheet.createRow(rowIdx++);
	   
	        row.createCell(header.get("ShipDate")).setCellValue(shipment.getShipDate().getShippingDate());
	        row.createCell(header.get("Email")).setCellValue(shipment.getCustomer().getEmail());
	        row.createCell(header.get("WechatId")).setCellValue(shipment.getCustomer().getWechatId());
	        row.createCell(header.get("ShipingCompany")).setCellValue(shipment.getShipingCompany());
	        row.createCell(header.get("TrackingNumber")).setCellValue(shipment.getTrackingNumber());
	        row.createCell(header.get("Description")).setCellValue(shipment.getDescription());
	        row.createCell(header.get("PackageValue")).setCellValue(shipment.getPackageValue());
	        row.createCell(header.get("Weight")).setCellValue(shipment.getWeight());
	        row.createCell(header.get("Dimension")).setCellValue(shipment.getDimension());
	        row.createCell(header.get("Unit")).setCellValue(shipment.getUnit());
	        row.createCell(header.get("Unit_Price")).setCellValue(shipment.getUnit_price());
	        row.createCell(header.get("Shipping_Price")).setCellValue(shipment.getShipping_price());
	        row.createCell(header.get("Status")).setCellValue(shipment.getStatus().toString());
	        row.createCell(header.get("CreateDate")).setCellValue(shipment.getCreateDate());
	        row.createCell(header.get("Note")).setCellValue(shipment.getNote());      
	        row.createCell(header.get("DeliveryMethod")).setCellValue(shipment.getDeliveryMethod()); 
	        row.createCell(header.get("PickupLocation")).setCellValue(shipment.getPickupLocation()  != null? shipment.getPickupLocation().getName():""); 
	        row.createCell(header.get("DeliveryAddress")).setCellValue(shipment.getDeliveryAddress()); 
	        row.createCell(header.get("DeliveryCity")).setCellValue(shipment.getDeliveryCity()); 
	        row.createCell(header.get("DeliveryProvince")).setCellValue(shipment.getDeliveryProvince()); 
	        row.createCell(header.get("DeliveryPostCode")).setCellValue(shipment.getDeliveryPostCode());
	        
	        row.getCell(header.get("CreateDate")).setCellStyle(dateCellStyle);
	      }
	   
	      workbook.write(out);
	      return new ByteArrayInputStream(out.toByteArray());
	}

	@Override
	public List<Shipment> findByStatus(PackageStatus packageStatus) {
		return ShipmentRepository.findByStatus(packageStatus);
	}

}
