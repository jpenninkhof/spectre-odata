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
import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmConcurrencyMode;
import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.EdmTargetPath;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.Association;
import org.apache.olingo.odata2.api.edm.provider.AssociationEnd;
import org.apache.olingo.odata2.api.edm.provider.AssociationSet;
import org.apache.olingo.odata2.api.edm.provider.AssociationSetEnd;
import org.apache.olingo.odata2.api.edm.provider.ComplexProperty;
import org.apache.olingo.odata2.api.edm.provider.ComplexType;
import org.apache.olingo.odata2.api.edm.provider.CustomizableFeedMappings;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.edm.provider.EntityContainer;
import org.apache.olingo.odata2.api.edm.provider.EntityContainerInfo;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.Facets;
import org.apache.olingo.odata2.api.edm.provider.FunctionImport;
import org.apache.olingo.odata2.api.edm.provider.Key;
import org.apache.olingo.odata2.api.edm.provider.NavigationProperty;
import org.apache.olingo.odata2.api.edm.provider.Property;
import org.apache.olingo.odata2.api.edm.provider.PropertyRef;
import org.apache.olingo.odata2.api.edm.provider.ReturnType;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.api.edm.provider.SimpleProperty;
import org.apache.olingo.odata2.api.exception.ODataException;

public class SpectreEdmProvider extends EdmProvider {

  static final String ENTITY_SET_NAME_CATEGORIES = "Categories";	
  static final String ENTITY_SET_NAME_SUPPLIERS = "Suppliers";
  static final String ENTITY_SET_NAME_PRODUCTS = "Products";
  static final String ENTITY_NAME_SUPPLIER = "Supplier";
  static final String ENTITY_NAME_CATEGORY = "Category";
  static final String ENTITY_NAME_PRODUCT = "Product";

  private static final String NAMESPACE = "org.apache.olingo.odata2.ODataProducts";

  private static final FullQualifiedName ENTITY_TYPE_1_1 = new FullQualifiedName(NAMESPACE, ENTITY_NAME_PRODUCT);
  private static final FullQualifiedName ENTITY_TYPE_1_2 = new FullQualifiedName(NAMESPACE, ENTITY_NAME_SUPPLIER);
  private static final FullQualifiedName ENTITY_TYPE_1_3 = new FullQualifiedName(NAMESPACE, ENTITY_NAME_CATEGORY);

  private static final FullQualifiedName COMPLEX_TYPE = new FullQualifiedName(NAMESPACE, "Address");

  private static final FullQualifiedName ASSOCIATION_PRODUCT_SUPPLIER = new FullQualifiedName(NAMESPACE, "Product_Supplier_Supplier_Products");
  private static final FullQualifiedName ASSOCIATION_PRODUCT_CATEGORY = new FullQualifiedName(NAMESPACE, "Product_Category_Category_Products");

  private static final String ROLE_1_2 = "Product_Supplier";
  private static final String ROLE_2_1 = "Supplier_Products";
  private static final String ROLE_1_3 = "Product_Category";
  private static final String ROLE_3_1 = "Supplier_Products";

  private static final String ENTITY_CONTAINER = "ODataProductsEntityContainer";

  private static final String ASSOCIATION_SET = "Products_Suppliers";

  private static final String FUNCTION_IMPORT = "NumberOfProducts";

  @Override
  public List<Schema> getSchemas() throws ODataException {
    List<Schema> schemas = new ArrayList<Schema>();

    Schema schema = new Schema();
    schema.setNamespace(NAMESPACE);

    List<EntityType> entityTypes = new ArrayList<EntityType>();
    entityTypes.add(getEntityType(ENTITY_TYPE_1_1));
    entityTypes.add(getEntityType(ENTITY_TYPE_1_2));
    entityTypes.add(getEntityType(ENTITY_TYPE_1_3));
    schema.setEntityTypes(entityTypes);

    List<ComplexType> complexTypes = new ArrayList<ComplexType>();
    complexTypes.add(getComplexType(COMPLEX_TYPE));
    schema.setComplexTypes(complexTypes);

    List<Association> associations = new ArrayList<Association>();
    associations.add(getAssociation(ASSOCIATION_PRODUCT_SUPPLIER));
    associations.add(getAssociation(ASSOCIATION_PRODUCT_CATEGORY));
    schema.setAssociations(associations);

    List<EntityContainer> entityContainers = new ArrayList<EntityContainer>();
    EntityContainer entityContainer = new EntityContainer();
    entityContainer.setName(ENTITY_CONTAINER).setDefaultEntityContainer(true);

    List<EntitySet> entitySets = new ArrayList<EntitySet>();
    entitySets.add(getEntitySet(ENTITY_CONTAINER, ENTITY_SET_NAME_PRODUCTS));
    entitySets.add(getEntitySet(ENTITY_CONTAINER, ENTITY_SET_NAME_SUPPLIERS));
    entitySets.add(getEntitySet(ENTITY_CONTAINER, ENTITY_SET_NAME_CATEGORIES));
    entityContainer.setEntitySets(entitySets);

    List<AssociationSet> associationSets = new ArrayList<AssociationSet>();
    associationSets.add(getAssociationSet(ENTITY_CONTAINER, ASSOCIATION_PRODUCT_SUPPLIER, ENTITY_SET_NAME_SUPPLIERS, ROLE_2_1));
    associationSets.add(getAssociationSet(ENTITY_CONTAINER, ASSOCIATION_PRODUCT_CATEGORY, ENTITY_SET_NAME_CATEGORIES, ROLE_3_1));
    entityContainer.setAssociationSets(associationSets);

    List<FunctionImport> functionImports = new ArrayList<FunctionImport>();
    functionImports.add(getFunctionImport(ENTITY_CONTAINER, FUNCTION_IMPORT));
    entityContainer.setFunctionImports(functionImports);

    entityContainers.add(entityContainer);
    schema.setEntityContainers(entityContainers);

    schemas.add(schema);

    return schemas;
  }

  @Override
  public EntityType getEntityType(final FullQualifiedName edmFQName) throws ODataException {
    if (NAMESPACE.equals(edmFQName.getNamespace())) {

      if (ENTITY_TYPE_1_1.getName().equals(edmFQName.getName())) {

        // Properties
        List<Property> properties = new ArrayList<Property>();
        properties.add(new SimpleProperty().setName("Id").setType(EdmSimpleTypeKind.Int32).setFacets(
            new Facets().setNullable(false)));
        properties.add(new SimpleProperty().setName("Name").setType(EdmSimpleTypeKind.String).setFacets(
            new Facets().setNullable(false).setMaxLength(100).setDefaultValue("Hugo"))
            .setCustomizableFeedMappings(
                new CustomizableFeedMappings().setFcTargetPath(EdmTargetPath.SYNDICATION_TITLE)));
        properties.add(new SimpleProperty().setName("SupplierId").setType(EdmSimpleTypeKind.Int32));
        properties.add(new SimpleProperty().setName("CategoryId").setType(EdmSimpleTypeKind.Int32));
        properties.add(new SimpleProperty().setName("ProductionUsage").setType(EdmSimpleTypeKind.Int32));
        properties.add(new SimpleProperty().setName("ConsumptionUsage").setType(EdmSimpleTypeKind.Int32));
        properties.add(new SimpleProperty().setName("ProductionRating").setType(EdmSimpleTypeKind.Int32));
        properties.add(new SimpleProperty().setName("ConsumptionRating").setType(EdmSimpleTypeKind.Int32));
        properties.add(new SimpleProperty().setName("Updated").setType(EdmSimpleTypeKind.DateTime)
            .setFacets(new Facets().setNullable(false).setConcurrencyMode(EdmConcurrencyMode.Fixed))
            .setCustomizableFeedMappings(
                new CustomizableFeedMappings().setFcTargetPath(EdmTargetPath.SYNDICATION_UPDATED)));

        // Navigation Properties
        List<NavigationProperty> navigationProperties = new ArrayList<NavigationProperty>();
        navigationProperties.add(new NavigationProperty().setName("Supplier")
            .setRelationship(ASSOCIATION_PRODUCT_SUPPLIER).setFromRole(ROLE_1_2).setToRole(ROLE_2_1));
        navigationProperties.add(new NavigationProperty().setName("Category")
            .setRelationship(ASSOCIATION_PRODUCT_CATEGORY).setFromRole(ROLE_1_3).setToRole(ROLE_3_1));

        // Key
        List<PropertyRef> keyProperties = new ArrayList<PropertyRef>();
        keyProperties.add(new PropertyRef().setName("Id"));
        Key key = new Key().setKeys(keyProperties);

        return new EntityType().setName(ENTITY_TYPE_1_1.getName())
            .setProperties(properties)
            .setKey(key)
            .setNavigationProperties(navigationProperties);

      } else if (ENTITY_TYPE_1_2.getName().equals(edmFQName.getName())) {

        // Properties
        List<Property> properties = new ArrayList<Property>();
        properties.add(new SimpleProperty().setName("Id").setType(EdmSimpleTypeKind.Int32).setFacets(
            new Facets().setNullable(false)));
        properties.add(new SimpleProperty().setName("Name").setType(EdmSimpleTypeKind.String).setFacets(
            new Facets().setNullable(false).setMaxLength(100))
            .setCustomizableFeedMappings(
                new CustomizableFeedMappings().setFcTargetPath(EdmTargetPath.SYNDICATION_TITLE)));
        properties.add(new ComplexProperty().setName("Address").setType(new FullQualifiedName(NAMESPACE, "Address")));
        properties.add(new SimpleProperty().setName("Updated").setType(EdmSimpleTypeKind.DateTime)
            .setFacets(new Facets().setNullable(false).setConcurrencyMode(EdmConcurrencyMode.Fixed))
            .setCustomizableFeedMappings(
                new CustomizableFeedMappings().setFcTargetPath(EdmTargetPath.SYNDICATION_UPDATED)));

        // Navigation Properties
        List<NavigationProperty> navigationProperties = new ArrayList<NavigationProperty>();
        navigationProperties.add(new NavigationProperty().setName("Products")
            .setRelationship(ASSOCIATION_PRODUCT_SUPPLIER).setFromRole(ROLE_2_1).setToRole(ROLE_1_2));

        // Key
        List<PropertyRef> keyProperties = new ArrayList<PropertyRef>();
        keyProperties.add(new PropertyRef().setName("Id"));
        Key key = new Key().setKeys(keyProperties);

        return new EntityType().setName(ENTITY_TYPE_1_2.getName())
            .setProperties(properties)
            .setKey(key)
            .setNavigationProperties(navigationProperties);

      } else if (ENTITY_TYPE_1_3.getName().equals(edmFQName.getName())) {

          // Properties
          List<Property> properties = new ArrayList<Property>();
          properties.add(new SimpleProperty().setName("Id").setType(EdmSimpleTypeKind.Int32).setFacets(
              new Facets().setNullable(false)));
          properties.add(new SimpleProperty().setName("Name").setType(EdmSimpleTypeKind.String).setFacets(
              new Facets().setNullable(false).setMaxLength(100))
              .setCustomizableFeedMappings(
                  new CustomizableFeedMappings().setFcTargetPath(EdmTargetPath.SYNDICATION_TITLE)));
          properties.add(new SimpleProperty().setName("Updated").setType(EdmSimpleTypeKind.DateTime)
              .setFacets(new Facets().setNullable(false).setConcurrencyMode(EdmConcurrencyMode.Fixed))
              .setCustomizableFeedMappings(
                  new CustomizableFeedMappings().setFcTargetPath(EdmTargetPath.SYNDICATION_UPDATED)));

          // Navigation Properties
          List<NavigationProperty> navigationProperties = new ArrayList<NavigationProperty>();
          navigationProperties.add(new NavigationProperty().setName("Products")
              .setRelationship(ASSOCIATION_PRODUCT_CATEGORY).setFromRole(ROLE_3_1).setToRole(ROLE_1_3));

          // Key
          List<PropertyRef> keyProperties = new ArrayList<PropertyRef>();
          keyProperties.add(new PropertyRef().setName("Id"));
          Key key = new Key().setKeys(keyProperties);

          return new EntityType().setName(ENTITY_TYPE_1_3.getName())
              .setProperties(properties)
              .setKey(key)
              .setNavigationProperties(navigationProperties);

        }    }

    return null;
  }

  @Override
  public ComplexType getComplexType(final FullQualifiedName edmFQName) throws ODataException {
    if (NAMESPACE.equals(edmFQName.getNamespace())) {
      if (COMPLEX_TYPE.getName().equals(edmFQName.getName())) {
        List<Property> properties = new ArrayList<Property>();
        properties.add(new SimpleProperty().setName("Street").setType(EdmSimpleTypeKind.String));
        properties.add(new SimpleProperty().setName("City").setType(EdmSimpleTypeKind.String));
        properties.add(new SimpleProperty().setName("ZipCode").setType(EdmSimpleTypeKind.String));
        properties.add(new SimpleProperty().setName("Country").setType(EdmSimpleTypeKind.String));
        return new ComplexType().setName(COMPLEX_TYPE.getName()).setProperties(properties);
      }
    }

    return null;
  }

  @Override
  public Association getAssociation(final FullQualifiedName edmFQName) throws ODataException {
    if (NAMESPACE.equals(edmFQName.getNamespace())) {
      if (ASSOCIATION_PRODUCT_SUPPLIER.getName().equals(edmFQName.getName())) {
        return new Association().setName(ASSOCIATION_PRODUCT_SUPPLIER.getName())
            .setEnd1(
                new AssociationEnd().setType(ENTITY_TYPE_1_1).setRole(ROLE_1_2).setMultiplicity(EdmMultiplicity.MANY))
            .setEnd2(
                new AssociationEnd().setType(ENTITY_TYPE_1_2).setRole(ROLE_2_1).setMultiplicity(EdmMultiplicity.ONE));
      } else if (ASSOCIATION_PRODUCT_CATEGORY.getName().equals(edmFQName.getName())) {
        return new Association().setName(ASSOCIATION_PRODUCT_SUPPLIER.getName())
	        .setEnd1(
	            new AssociationEnd().setType(ENTITY_TYPE_1_1).setRole(ROLE_1_3).setMultiplicity(EdmMultiplicity.MANY))
	        .setEnd2(
	            new AssociationEnd().setType(ENTITY_TYPE_1_3).setRole(ROLE_3_1).setMultiplicity(EdmMultiplicity.ONE));
	  }

    }
    return null;
  }

  @Override
  public EntitySet getEntitySet(final String entityContainer, final String name) throws ODataException {
    if (ENTITY_CONTAINER.equals(entityContainer)) {
      if (ENTITY_SET_NAME_PRODUCTS.equals(name)) {
        return new EntitySet().setName(name).setEntityType(ENTITY_TYPE_1_1);
      } else if (ENTITY_SET_NAME_SUPPLIERS.equals(name)) {
        return new EntitySet().setName(name).setEntityType(ENTITY_TYPE_1_2);
      } else if (ENTITY_SET_NAME_CATEGORIES.equals(name)) {
          return new EntitySet().setName(name).setEntityType(ENTITY_TYPE_1_3);
        }
    }
    return null;
  }

  @Override
  public AssociationSet getAssociationSet(final String entityContainer, final FullQualifiedName association,
      final String sourceEntitySetName, final String sourceEntitySetRole) throws ODataException {
    if (ENTITY_CONTAINER.equals(entityContainer)) {
      if (ASSOCIATION_PRODUCT_SUPPLIER.equals(association)) {
        return new AssociationSet().setName(ASSOCIATION_SET)
            .setAssociation(ASSOCIATION_PRODUCT_SUPPLIER)
            .setEnd1(new AssociationSetEnd().setRole(ROLE_2_1).setEntitySet(ENTITY_SET_NAME_SUPPLIERS))
            .setEnd2(new AssociationSetEnd().setRole(ROLE_1_2).setEntitySet(ENTITY_SET_NAME_PRODUCTS));
      } else if (ASSOCIATION_PRODUCT_CATEGORY.equals(association)) {
          return new AssociationSet().setName(ASSOCIATION_SET)
              .setAssociation(ASSOCIATION_PRODUCT_CATEGORY)
              .setEnd1(new AssociationSetEnd().setRole(ROLE_3_1).setEntitySet(ENTITY_SET_NAME_CATEGORIES))
              .setEnd2(new AssociationSetEnd().setRole(ROLE_1_3).setEntitySet(ENTITY_SET_NAME_PRODUCTS));
        } 
    }
    return null;
  }

  @Override
  public FunctionImport getFunctionImport(final String entityContainer, final String name) throws ODataException {
    if (ENTITY_CONTAINER.equals(entityContainer)) {
      if (FUNCTION_IMPORT.equals(name)) {
        return new FunctionImport().setName(name)
            .setReturnType(new ReturnType().setTypeName(ENTITY_TYPE_1_1).setMultiplicity(EdmMultiplicity.MANY))
            .setHttpMethod("GET");
      }
    }
    return null;
  }

  @Override
  public EntityContainerInfo getEntityContainerInfo(final String name) throws ODataException {
    if (name == null || "ODataProductsEntityContainer".equals(name)) {
      return new EntityContainerInfo().setName("ODataProductsEntityContainer").setDefaultEntityContainer(true);
    }

    return null;
  }
}
