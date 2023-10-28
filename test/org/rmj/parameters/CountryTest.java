/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rmj.parameters;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rmj.appdriver.GCrypt;
import static org.junit.Assert.*;
import org.rmj.appdriver.GRider;
import org.rmj.integsys.pojo.UnitCountry;

/**
 *
 * @author kalyptus
 */
public class CountryTest {
    private static GRider grider;
    private static String lsNewId;

    public CountryTest() {
    }

   @BeforeClass
   public static void setUpClass() throws Exception {
      grider = new GRider("IntegSys");
      System.out.println("Branch: " + grider.getBranchName());
      grider.logUser("gRider", "33070005");
      System.out.println("Branch: " + grider.getBranchName());
   }

   @AfterClass
   public static void tearDownClass() throws Exception {
   }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

   /**
    * Test of newRecord method, of class Country.
    */
   @Test
   public void testNewRecord() {
      System.out.println("newRecord");
      Country instance = new Country();
      instance.setGRider(grider);
      UnitCountry result = instance.newRecord();
      System.out.println("New Country:" + result.getCountryCde());
      assertNotNull(result);
   }

//   /**
//    * Test of openRecord method, of class Country.
//    */
   @Test
   public void testOpenRecord() {
      System.out.println("openRecord");
      String fstransNox = "01";
      Country instance = new Country();
      instance.setGRider(grider);
      UnitCountry result = instance.openRecord(fstransNox);
      assertEquals(result.getCountryNme(), "PHILIPPINES");
   }

   @Test
   public void testOpenRecordNone() {
      System.out.println("testOpenRecordNone");
      String fstransNox = "100";
      Country instance = new Country();
      instance.setGRider(grider);
      UnitCountry result = instance.openRecord(fstransNox);
      assertNull(result.getCountryCde());
   }

   //
//   /**
//    * Test of saveRecord method, of class Country.
//    */
   @Test
   public void testSaveRecordNew() {
      System.out.println("saveNewRecord");
      String fsTransNox = "";
      Country instance = new Country();
      instance.setGRider(grider);
      UnitCountry result1 = instance.newRecord();
      result1.setCountryNme("Jerusalem");
//      result1.setNational("");
      UnitCountry result2 = instance.saveRecord(result1, fsTransNox);
      lsNewId = (String) result2.getValue(1);
      assertEquals(result1.getCountryNme(), result2.getCountryNme());
   }

   @Test
   public void testSaveRecordUpdate() {
      System.out.println("SaveRecordUpdate");
      Country instance = new Country();
      instance.setGRider(grider);
      System.out.println(lsNewId);
      UnitCountry result1 = instance.openRecord(lsNewId);
      result1.setCountryNme("New Jerusalem");
      UnitCountry result2 = instance.saveRecord(result1, lsNewId);
      System.out.println(instance.getErrMsg() + instance.getMessage());
      GCrypt loCrypt = new GCrypt();
      System.out.println(loCrypt.decrypt(result2.getModifiedBy()));
      assertEquals(result1.getCountryNme(), result2.getCountryNme());
   }

   /**
    * Test of deactivateRecord method, of class Country.
    */
   @Test
   public void testDeactivateRecordActive() {
      System.out.println("DeactivateRecord-Active");
      Country instance = new Country();
      instance.setGRider(grider);
      boolean expResult = true;
      boolean result = instance.deactivateRecord(lsNewId);
      assertEquals(expResult, result);
   }

   @Test
   public void testDeactivateRecordInActive() {
      System.out.println("DeactivateRecord-InActive");
      Country instance = new Country();
      instance.setGRider(grider);
      boolean expResult = false;
      boolean result = instance.deactivateRecord(lsNewId);
      assertEquals(expResult, result);
   }

   /**
    * Test of activateRecord method, of class Country.
    */
   @Test
   public void testActivateRecordInActive() {
      System.out.println("ActivateRecordInActive");
      Country instance = new Country();
      instance.setGRider(grider);
      boolean expResult = true;
      boolean result = instance.activateRecord(lsNewId);
      assertEquals(expResult, result);
   }
   /**
    * Test of activateRecord method, of class Country.
    */
   @Test
   public void testActivateRecordActive() {
      System.out.println("ActivateRecordActive");
      Country instance = new Country();
      instance.setGRider(grider);
      boolean expResult = false;
      boolean result = instance.activateRecord(lsNewId);
      assertEquals(expResult, result);
   }

   /**
    * Test of deleteRecord method, of class Country.
    */
   @Test
   public void testDeleteRecord() {
      System.out.println("deleteRecord");
      Country instance = new Country();
      instance.setGRider(grider);
      boolean expResult = true;
      boolean result = instance.deleteRecord(lsNewId);
      assertEquals(expResult, result);
   }
   /**
    * Test of deleteRecord method, of class Country.
    */
   @Test
   public void testDeleteRecordNone() {
      System.out.println("deleteRecordNone");
      Country instance = new Country();
      instance.setGRider(grider);
      boolean expResult = false;
      boolean result = instance.deleteRecord(lsNewId);
      assertEquals(expResult, result);
   }
}