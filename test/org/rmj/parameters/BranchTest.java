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
import org.rmj.integsys.pojo.UnitBranch;

/**
 *
 * @author kalyptus
 */
public class BranchTest {
    private static GRider grider;
    private static String lsNewId;

    public BranchTest() {
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
    * Test of newRecord method, of class Branch.
    */

   @Test
   public void testNewRecord() {
      System.out.println("newRecord");
      Branch instance = new Branch();
      instance.setGRider(grider);
      UnitBranch result = instance.newRecord();
      System.out.println("New Branch:" + result.getBranchCd());
      assertNotNull(result);
   }

//   /**
//    * Test of openRecord method, of class Branch.
//    */
   @Test
   public void testOpenRecord() {
      System.out.println("openRecord");
      String fstransNox = "01";
      Branch instance = new Branch();
      instance.setGRider(grider);
      UnitBranch result = instance.openRecord(fstransNox);
      assertEquals(result.getBranchNm(), "GMC Main Office");
   }

   @Test
   public void testOpenRecordNone() {
      System.out.println("testOpenRecordNone");
      String fstransNox = "100";
      Branch instance = new Branch();
      instance.setGRider(grider);
      UnitBranch result = instance.openRecord(fstransNox);
      assertNull(result.getBranchCd());
   }

   //
//   /**
//    * Test of saveRecord method, of class Branch.
//    */
   @Test
   public void testSaveRecordNew() {
      System.out.println("saveNewRecord");
      String fsTransNox = "";
      Branch instance = new Branch();
      instance.setGRider(grider);
      UnitBranch result1 = instance.newRecord();
      result1.setBranchNm("UEMI Cebu");
      result1.setCompanyID("01");
      result1.setManager("29090004");
      result1.setSellCode("AA");
      result1.setAddress("111 Numero Uno");
      result1.setTownID("0838");
      UnitBranch result2 = instance.saveRecord(result1, fsTransNox);
      lsNewId = (String) result2.getValue(1);
      assertEquals(result1.getBranchNm(), result2.getBranchNm());
   }

   @Test
   public void testSaveRecordUpdate() {
      System.out.println("SaveRecordUpdate");
      Branch instance = new Branch();
      instance.setGRider(grider);
      UnitBranch result1 = instance.openRecord(lsNewId);
      result1.setAddress("222 Numero Dos");
      UnitBranch result2 = instance.saveRecord(result1, lsNewId);
      System.out.println("Error: " + instance.getErrMsg() + instance.getMessage());
      System.out.println(result2.getBranchNm());
      GCrypt loCrypt = new GCrypt();
      System.out.println(loCrypt.decrypt(result2.getModifiedBy()));
      assertEquals(result1.getBranchNm(), result2.getBranchNm());
   }

   /**
    * Test of deactivateRecord method, of class Branch.
    */
   @Test
   public void testDeactivateRecordActive() {
      System.out.println("DeactivateRecord-Active");
      Branch instance = new Branch();
      instance.setGRider(grider);
      boolean expResult = true;
      boolean result = instance.deactivateRecord(lsNewId);
      assertEquals(expResult, result);
   }

   @Test
   public void testDeactivateRecordInActive() {
      System.out.println("DeactivateRecord-InActive");
      Branch instance = new Branch();
      instance.setGRider(grider);
      boolean expResult = false;
      boolean result = instance.deactivateRecord(lsNewId);
      assertEquals(expResult, result);
   }

   /**
    * Test of activateRecord method, of class Branch.
    */
   @Test
   public void testActivateRecordInActive() {
      System.out.println("ActivateRecordInActive");
      Branch instance = new Branch();
      instance.setGRider(grider);
      boolean expResult = true;
      boolean result = instance.activateRecord(lsNewId);
      assertEquals(expResult, result);
   }
   /**
    * Test of activateRecord method, of class Branch.
    */
   @Test
   public void testActivateRecordActive() {
      System.out.println("ActivateRecordActive");
      Branch instance = new Branch();
      instance.setGRider(grider);
      boolean expResult = false;
      boolean result = instance.activateRecord(lsNewId);
      assertEquals(expResult, result);
   }

   /**
    * Test of deleteRecord method, of class Branch.
    */
   @Test
   public void testDeleteRecord() {
      System.out.println("deleteRecord");
      Branch instance = new Branch();
      instance.setGRider(grider);
      boolean expResult = true;
      boolean result = instance.deleteRecord(lsNewId);
      assertEquals(expResult, result);
   }
   /**
    * Test of deleteRecord method, of class Branch.
    */
   @Test
   public void testDeleteRecordNone() {
      System.out.println("deleteRecordNone");
      Branch instance = new Branch();
      instance.setGRider(grider);
      boolean expResult = false;
      boolean result = instance.deleteRecord(lsNewId);
      assertEquals(expResult, result);
   }
}