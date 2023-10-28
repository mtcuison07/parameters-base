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
import org.rmj.integsys.pojo.UnitProvince;

/**
 *
 * @author kalyptus
 */
public class ProvinceTest {
    private static GRider grider;
    private static String lsNewId = new String();
    
    public ProvinceTest() {
    }

   @BeforeClass
   public static void setUpClass() throws Exception {
      grider = new GRider("IntegSys");
      grider.logUser("IntegSys", "01050044");
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
    * Test of newRecord method, of class Province.
    */
   @Test
   public void testGRider() {
     System.out.println("getUserID");
     String fsUserID = "01050044";
     String fsProductID = "IntegSys";
     String result = grider.getUserID();
     System.out.println(result);
     assertEquals(fsUserID, result);
   }

   @Test
   public void testNewRecord() {
      System.out.println("newRecord");
      Province instance = new Province();
      instance.setGRider(grider);
      UnitProvince result = instance.newRecord();
      System.out.println("New Province:" + result.getProvID());
      assertNotNull(result);
   }

//   /**
//    * Test of openRecord method, of class Province.
//    */
   @Test
   public void testOpenRecord() {
      System.out.println("openRecord");
      String fstransNox = "01";
      Province instance = new Province();
      instance.setGRider(grider);
      UnitProvince result = instance.openRecord(fstransNox);
      assertEquals(result.getProvName(), "Pangasinan");
   }

   @Test
   public void testOpenRecordNone() {
      System.out.println("testOpenRecordNone");
      String fstransNox = "100";
      Province instance = new Province();
      instance.setGRider(grider);
      UnitProvince result = instance.openRecord(fstransNox);
      assertNull(result.getProvID());
   }

   //
//   /**
//    * Test of saveRecord method, of class Province.
//    */
   @Test
   public void testSaveRecordNew() {
      System.out.println("saveNewRecord");
      String fsTransNox = "";
      Province instance = new Province();
      instance.setGRider(grider);
      UnitProvince result1 = instance.newRecord();
      result1.setProvName("Minesota");
      UnitProvince result2 = instance.saveRecord(result1, fsTransNox);
      lsNewId = (String) result2.getValue(1);
      System.out.print("Province #1:" + lsNewId);
      assertEquals(result1.getProvName(), result2.getProvName());
   }

   @Test
   public void testSaveRecordUpdate() {
      System.out.println("SaveRecordUpdate");
      Province instance = new Province();
      instance.setGRider(grider);
      System.out.print("Province:" + lsNewId);
      UnitProvince result1 = instance.openRecord(lsNewId);
      result1.setProvName("Mondragon");
      UnitProvince result2 = instance.saveRecord(result1, lsNewId);
      System.out.println(instance.getErrMsg() + instance.getMessage());
      GCrypt loCrypt = new GCrypt();
      System.out.println(loCrypt.decrypt(result2.getModifiedBy()));
      assertEquals(result1.getProvName(), result2.getProvName());
   }

   /**
    * Test of deactivateRecord method, of class Province.
    */
   @Test
   public void testDeactivateRecordActive() {
      System.out.println("DeactivateRecord-Active");
      Province instance = new Province();
      instance.setGRider(grider);
      boolean expResult = true;
      boolean result = instance.deactivateRecord(lsNewId);
      assertEquals(expResult, result);
   }

   @Test
   public void testDeactivateRecordInActive() {
      System.out.println("DeactivateRecord-InActive");
      Province instance = new Province();
      instance.setGRider(grider);
      boolean expResult = false;
      boolean result = instance.deactivateRecord(lsNewId);
      assertEquals(expResult, result);
   }

   /**
    * Test of activateRecord method, of class Province.
    */
   @Test
   public void testActivateRecordInActive() {
      System.out.println("ActivateRecordInActive");
      Province instance = new Province();
      instance.setGRider(grider);
      boolean expResult = true;
      boolean result = instance.activateRecord(lsNewId);
      assertEquals(expResult, result);
   }
   /**
    * Test of activateRecord method, of class Province.
    */
   @Test
   public void testActivateRecordActive() {
      System.out.println("ActivateRecordActive");
      Province instance = new Province();
      instance.setGRider(grider);
      boolean expResult = false;
      boolean result = instance.activateRecord(lsNewId);
      assertEquals(expResult, result);
   }

   /**
    * Test of deleteRecord method, of class Province.
    */
   @Test
   public void testDeleteRecord() {
      System.out.println("deleteRecord");
      Province instance = new Province();
      instance.setGRider(grider);
      boolean expResult = true;
      boolean result = instance.deleteRecord(lsNewId);
      assertEquals(expResult, result);
   }
   /**
    * Test of deleteRecord method, of class Province.
    */
   @Test
   public void testDeleteRecordNone() {
      System.out.println("deleteRecordNone");
      Province instance = new Province();
      instance.setGRider(grider);
      boolean expResult = false;
      boolean result = instance.deleteRecord(lsNewId);
      assertEquals(expResult, result);
   }
}