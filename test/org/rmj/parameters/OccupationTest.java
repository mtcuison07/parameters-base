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
import org.rmj.integsys.pojo.UnitOccupation;

/**
 *
 * @author kalyptus
 */
public class OccupationTest {
    private static GRider grider;
    private static String lsNewId;

    public OccupationTest() {
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
    * Test of newRecord method, of class Occupation.
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
      Occupation instance = new Occupation();
      instance.setGRider(grider);
      UnitOccupation result = instance.newRecord();
      System.out.println("New Occupation:" + result.getOccptnID());
      assertNotNull(result);
   }

//   /**
//    * Test of openRecord method, of class Occupation.
//    */
   @Test
   public void testOpenRecord() {
      System.out.println("openRecord");
      String fstransNox = "09674";
      Occupation instance = new Occupation();
      instance.setGRider(grider);
      UnitOccupation result = instance.openRecord(fstransNox);
      assertEquals(result.getOccptnNm(), "Zookeeper ");
   }

   @Test
   public void testOpenRecordNone() {
      System.out.println("testOpenRecordNone");
      String fstransNox = "09677";
      Occupation instance = new Occupation();
      instance.setGRider(grider);
      UnitOccupation result = instance.openRecord(fstransNox);
      assertNull(result.getOccptnID());
   }

   //
//   /**
//    * Test of saveRecord method, of class Occupation.
//    */
   @Test
   public void testSaveRecordNew() {
      System.out.println("saveNewRecord");
      String fsTransNox = "";
      Occupation instance = new Occupation();
      instance.setGRider(grider);
      UnitOccupation result1 = instance.newRecord();
      result1.setOccptnNm("Classroom Teacher");
      UnitOccupation result2 = instance.saveRecord(result1, fsTransNox);
      lsNewId = (String) result2.getValue(1);
      assertEquals(result1.getOccptnNm(), result2.getOccptnNm());
   }

   @Test
   public void testSaveRecordUpdate() {
      System.out.println("SaveRecordUpdate");
      Occupation instance = new Occupation();
      instance.setGRider(grider);
      UnitOccupation result1 = instance.openRecord(lsNewId);
      result1.setOccptnNm("Classroom Adviser");
      UnitOccupation result2 = instance.saveRecord(result1, lsNewId);
      System.out.println(instance.getErrMsg() + instance.getMessage());
      GCrypt loCrypt = new GCrypt();
      System.out.println(loCrypt.decrypt(result2.getModifiedBy()));
      assertEquals(result1.getOccptnNm(), result2.getOccptnNm());
   }

   /**
    * Test of deactivateRecord method, of class Occupation.
    */
   @Test
   public void testDeactivateRecordActive() {
      System.out.println("DeactivateRecord-Active");
      Occupation instance = new Occupation();
      instance.setGRider(grider);
      boolean expResult = true;
      boolean result = instance.deactivateRecord(lsNewId);
      assertEquals(expResult, result);
   }

   @Test
   public void testDeactivateRecordInActive() {
      System.out.println("DeactivateRecord-InActive");
      Occupation instance = new Occupation();
      instance.setGRider(grider);
      boolean expResult = false;
      boolean result = instance.deactivateRecord(lsNewId);
      assertEquals(expResult, result);
   }

   /**
    * Test of activateRecord method, of class Occupation.
    */
   @Test
   public void testActivateRecordInActive() {
      System.out.println("ActivateRecordInActive");
      Occupation instance = new Occupation();
      instance.setGRider(grider);
      boolean expResult = true;
      boolean result = instance.activateRecord(lsNewId);
      assertEquals(expResult, result);
   }
   /**
    * Test of activateRecord method, of class Occupation.
    */
   @Test
   public void testActivateRecordActive() {
      System.out.println("ActivateRecordActive");
      Occupation instance = new Occupation();
      instance.setGRider(grider);
      boolean expResult = false;
      boolean result = instance.activateRecord(lsNewId);
      assertEquals(expResult, result);
   }

   /**
    * Test of deleteRecord method, of class Occupation.
    */
   @Test
   public void testDeleteRecord() {
      System.out.println("deleteRecord");
      Occupation instance = new Occupation();
      instance.setGRider(grider);
      boolean expResult = true;
      boolean result = instance.deleteRecord(lsNewId);
      assertEquals(expResult, result);
   }
   /**
    * Test of deleteRecord method, of class Occupation.
    */
   @Test
   public void testDeleteRecordNone() {
      System.out.println("deleteRecordNone");
      Occupation instance = new Occupation();
      instance.setGRider(grider);
      boolean expResult = false;
      boolean result = instance.deleteRecord(lsNewId);
      assertEquals(expResult, result);
   }
}