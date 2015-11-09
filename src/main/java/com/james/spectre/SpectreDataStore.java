/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 ******************************************************************************/
package com.james.spectre;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class SpectreDataStore {

  // Data accessors
  public Map<String, Object> getProduct(final int id) {
    Map<String, Object> data = null;

    Calendar updated = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

    switch (id) {
    case 1: updated.set(2015, 11, 9); data = createProduct(1, "Golf", 1, 2, 0, 0, 3, 0, updated); break;
    case 2: updated.set(2015, 11, 9); data = createProduct(2, "Passat", 1, 3, 0, 0, 2, 0, updated); break;
    case 3: updated.set(2015, 11, 9); data = createProduct(3, "Polo", 1, 1, 0, 0, 4, 0, updated); break;
    case 4: updated.set(2015, 11, 9); data = createProduct(4, "Up", 1, 1, 0, 0, 5, 0, updated); break;
    case 5: updated.set(2015, 11, 9); data = createProduct(5, "Phaeton", 1, 4, 0, 0, 1, 0, updated); break;
    case 6: updated.set(2015, 11, 9); data = createProduct(6, "Leon", 2, 2, 0, 0, 4, 0, updated); break;
    case 7: updated.set(2015, 11, 9); data = createProduct(7, "Octavia", 3, 2, 0, 0, 4, 0, updated); break;
    case 8: updated.set(2015, 11, 9); data = createProduct(8, "Punto", 4, 2, 0, 0, 4, 0, updated); break;
    case 9: updated.set(2015, 11, 9); data = createProduct(9, "Punto", 5, 1, 0, 0, 2, 0, updated); break;
    default: break;
    }

    return data;
  }

  private Map<String, Object> createProduct(
	  final int productId, final String name, final int supplierId, final int categoryId,
      final int productionUsage, int consumptionUsage, int productionRating, int consumptionRating, 
      final Calendar updated) {
    Map<String, Object> data = new HashMap<String, Object>();

    data.put("Id", productId);
    data.put("Name", name);
    data.put("SupplierId", supplierId);
    data.put("CategoryId", categoryId);
    data.put("ProductionUsage", productionUsage);
    data.put("ConsumptionUsage", consumptionUsage);
    data.put("ProductionRating", productionRating);
    data.put("ConsumptionRating", consumptionRating);
    data.put("Updated", updated);

    return data;
  }

  public Map<String, Object> getSupplier(final int id) {
    Map<String, Object> data = null;
    Calendar date = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    switch (id) {
      case 1: date.set(2015, 11, 9); data = createSupplier(1, "Volkswagen", createAddress("Star Street 137", "Stuttgart", "70173", "Germany"), date); break;
      case 2: date.set(2015, 11, 9); data = createSupplier(2, "Seat", createAddress("Horse Street 1", "Maranello", "41053", "Italy"), date); break;
      case 3: date.set(2015, 11, 9); data = createSupplier(3, "Skoda", createAddress("Downing Street 5", "London", "373823", "United Kingdom"), date); break;
      case 4: date.set(2015, 11, 9); data = createSupplier(4, "Fiat", createAddress("Strada Grande 10", "Milano", "18927", "Italy"), date); break;
      case 5: date.set(2015, 11, 9); data = createSupplier(4, "Toyota", createAddress("Ninja Sushi 10", "Tokyo", "182398", "Jappan"), date); break;
    default:
      break;
    }
    return data;
  }


  public Map<String, Object> getCategory(final int id) {
    Map<String, Object> data = null;
    Calendar date = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    switch (id) {
	  case 1: date.set(2015, 11, 9); data = createCategory(1, "Cars - Class A", date); break;
	  case 2: date.set(2015, 11, 9); data = createCategory(2, "Cars - Class B", date); break;
	  case 3: date.set(2015, 11, 9); data = createCategory(3, "Cars - Class C", date); break;
	  case 4: date.set(2015, 11, 9); data = createCategory(4, "Cars - Class D", date); break;
    default:
      break;
    }
    return data;
  }

  private Map<String, Object> createSupplier(final int id, final String name, final Map<String, Object> address,
      final Calendar updated) {
    Map<String, Object> data = new HashMap<String, Object>();
    data.put("Id", id);
    data.put("Name", name);
    data.put("Address", address);
    data.put("Updated", updated);
    return data;
  }

  private Map<String, Object> createCategory(final int id, final String name, 
      final Calendar updated) {
    Map<String, Object> data = new HashMap<String, Object>();
    data.put("Id", id);
    data.put("Name", name);
    data.put("Updated", updated);
    return data;
  }

  private Map<String, Object> createAddress(final String street, final String city, final String zipCode,
      final String country) {
    Map<String, Object> address = new HashMap<String, Object>();
    address.put("Street", street);
    address.put("City", city);
    address.put("ZipCode", zipCode);
    address.put("Country", country);
    return address;
  }

  public List<Map<String, Object>> getProducts() {
    List<Map<String, Object>> products = new ArrayList<Map<String, Object>>();
    products.add(getProduct(1));
    products.add(getProduct(2));
    products.add(getProduct(3));
    products.add(getProduct(4));
    products.add(getProduct(5));
    products.add(getProduct(6));
    products.add(getProduct(7));
    products.add(getProduct(8));
    return products;
  }

  public List<Map<String, Object>> getSuppliers() {
    List<Map<String, Object>> suppliers = new ArrayList<Map<String, Object>>();
    suppliers.add(getSupplier(1));
    suppliers.add(getSupplier(2));
    suppliers.add(getSupplier(3));
    suppliers.add(getSupplier(4));
    suppliers.add(getSupplier(5));
    return suppliers;
  }

  public List<Map<String, Object>> getCategories() {
	    List<Map<String, Object>> categories = new ArrayList<Map<String, Object>>();
	    categories.add(getCategory(1));
	    categories.add(getCategory(2));
	    categories.add(getCategory(3));
	    categories.add(getCategory(4));
	    return categories;
	  }

  public List<Map<String, Object>> getProductsFor(final int supplierId) {
    List<Map<String, Object>> products = getProducts();
    List<Map<String, Object>> productsForSupplier = new ArrayList<Map<String, Object>>();

    for (Map<String, Object> product : products) {
      if (Integer.valueOf(supplierId).equals(product.get("SupplierId"))) {
        productsForSupplier.add(product);
      }
    }

    return productsForSupplier;
  }

  public Map<String, Object> getSupplierFor(final int productId) {
    Map<String, Object> product = getProduct(productId);
    if (product != null) {
      Object supplierId = product.get("SupplierId");
      if (supplierId != null) {
        return getSupplier((Integer) supplierId);
      }
    }
    return null;
  }
  

  public Map<String, Object> getCategoryFor(final int productId) {
    Map<String, Object> product = getProduct(productId);
    if (product != null) {
      Object categoryId = product.get("CategoryId");
      if (categoryId != null) {
        return getCategory((Integer) categoryId);
      }
    }
    return null;
  }
    
}
