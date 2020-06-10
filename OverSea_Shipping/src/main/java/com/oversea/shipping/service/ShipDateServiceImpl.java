package com.oversea.shipping.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oversea.shipping.dao.ShipDateRepository;
import com.oversea.shipping.model.ShipDate;

@Service
public class ShipDateServiceImpl implements ShipDateService {

	private ShipDateRepository shipDateRepository;
	
	@Autowired
	public ShipDateServiceImpl(ShipDateRepository theshipDateRepository) {
		shipDateRepository = theshipDateRepository;
	}
	
	public List<ShipDate> findAllActive() {
		String pattern = "yyyy-MM-dd";
		DateFormat df = new SimpleDateFormat(pattern);
		return shipDateRepository.findByCutOffDateGreaterThanEqualAndActiveTrue(df.format(new Date()));
	}
	
	public List<ShipDate> findAll() {
		return shipDateRepository.findAllByOrderByShippingDateAsc();
	}

	public ShipDate findById(int theId) {
		Optional<ShipDate> result = shipDateRepository.findById(theId);
		
		ShipDate theshipDate = null;
		
		if (result.isPresent()) {
			theshipDate = result.get();
		}
		else {
			// we didn't find the shipDate
			throw new RuntimeException("Did not find shipDate id - " + theId);
		}
		
		return theshipDate;
	}

	public void save(ShipDate theshipDate) {
		shipDateRepository.save(theshipDate);
	}

	public void deleteById(int theId) {
		shipDateRepository.deleteById(theId);
	}

}






