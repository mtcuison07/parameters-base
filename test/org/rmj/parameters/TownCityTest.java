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
import org.rmj.integsys.pojo.UnitTown;

/**
 *
 * @author kalyptus
 */
public class TownCityTest {
    private static GRider grider;
    private static String lsNewId = new String();

    public TownCityTest() {
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
    * Test of newRecord method, of class TownCity.
    */
   @Test
   public void testNewRecord() {
      System.out.println("newRecord");
      TownCity instance = new TownCity();
      instance.setGRider(grider);
      UnitTown result = instance.newRecord();
      System.out.println("New TownCity:" + result.getTownID());
      assertNotNull(result);
   }

//   /**
//    * Test of openRecord method, of class TownCity.
//    */
   @Test
   public void testOpenRecord() {
      System.out.println("openRecord");
      String fstransNox = "0001";
      TownCity instance = new TownCity();
      instance.setGRider(grider);
      UnitTown result = instance.openRecord(fstransNox);
      assertEquals(result.getTownName(), "Bureau of Plant Industry");
   }

   @Test
   public void testOpenRecordNone() {
      System.out.println("testOpenRecordNone");
      String fstransNox = "100";
      TownCity instance = new TownCity();
      instance.setGRider(grider);
      UnitTown result = instance.openRecord(fstransNox);
      assertNull(result.getTownID());
   }

   //
//   /**
//    * Test of saveRecord method, of class TownCity.
//    */
   @Test
   public void testSaveRecordNew() {
      System.out.println("saveNewRecord");
      String fsTransNox = "";
      TownCity instance = new TownCity();
      instance.setGRider(grider);
      UnitTown result1 = instance.newRecord();
      result1.setTownName("Jerusalem");
//      result1.setNational("");
      UnitTown result2 = instance.saveRecord(result1, fsTransNox);
      lsNewId = (String) result2.getValue(1);
      assertEquals(result1.getTownName(), result2.getTownName());
   }

   @Test
   public void testSaveRecordUpdate() {
      System.out.println("SaveRecordUpdate");
      TownCity instance = new TownCity();
      instance.setGRider(grider);
      System.out.println(lsNewId);
      UnitTown result1 = instance.openRecord(lsNewId);
      result1.setTownName("New Jerusalem");
      UnitTown result2 = instance.saveRecord(result1, lsNewId);
      System.out.println(instance.getErrMsg() + instance.getMessage());
      GCrypt loCrypt = new GCrypt();
      System.out.println(loCrypt.decrypt(result2.getModifiedBy()));
      assertEquals(result1.getTownName(), result2.getTownName());
   }

   /**
    * Test of deactivateRecord method, of class TownCity.
    */
   @Test
   public void testDeactivateRecordActive() {
      System.out.println("DeactivateRecord-Active");
      TownCity instance = new TownCity();
      instance.setGRider(grider);
      boolean expResult = true;
      boolean result = instance.deactivateRecord(lsNewId);
      assertEquals(expResult, result);
   }

   @Test
   public void testDeactivateRecordInActive() {
      System.out.println("DeactivateRecord-InActive");
      TownCity instance = new TownCity();
      instance.setGRider(grider);
      boolean expResult = false;
      boolean result = instance.deactivateRecord(lsNewId);
      assertEquals(expResult, result);
   }

   /**
    * Test of activateRecord method, of class TownCity.
    */
   @Test
   public void testActivateRecordInActive() {
      System.out.println("ActivateRecordInActive");
      TownCity instance = new TownCity();
      instance.setGRider(grider);
      boolean expResult = true;
      boolean result = instance.activateRecord(lsNewId);
      assertEquals(expResult, result);
   }
   /**
    * Test of activateRecord method, of class TownCity.
    */
   @Test
   public void testActivateRecordActive() {
      System.out.println("ActivateRecordActive");
      TownCity instance = new TownCity();
      instance.setGRider(grider);
      boolean expResult = false;
      boolean result = instance.activateRecord(lsNewId);
      assertEquals(expResult, result);
   }

   /**
    * Test of deleteRecord method, of class TownCity.
    */
   @Test
   public void testDeleteRecordWithRoute() {
      System.out.println("deleteRecordWithRoute");
      TownCity instance = new TownCity();
      instance.setGRider(grider);
      UnitTown result1 = instance.openRecord(lsNewId);
      result1.setHasRoute("1");
      UnitTown result2 = instance.saveRecord(result1, lsNewId);
      boolean expResult = false;
      boolean result = instance.deleteRecord(lsNewId);
      result1.setHasRoute("0");
      result2 = instance.saveRecord(result1, lsNewId);

      assertEquals(expResult, result);
   }

   /**
    * Test of deleteRecord method, of class TownCity.
    */
   @Test
   public void testDeleteRecord() {
      System.out.println("deleteRecord");
      TownCity instance = new TownCity();
      instance.setGRider(grider);
      boolean expResult = true;
      boolean result = instance.deleteRecord(lsNewId);
      assertEquals(expResult, result);
   }

   /**
    * Test of deleteRecord method, of class TownCity.
    */
   @Test
   public void testDeleteRecordNone() {
      System.out.println("deleteRecordNone");
      TownCity instance = new TownCity();
      instance.setGRider(grider);
      boolean expResult = false;
      boolean result = instance.deleteRecord(lsNewId);
      assertEquals(expResult, result);
   }
}