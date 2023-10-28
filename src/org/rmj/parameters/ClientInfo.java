/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rmj.parameters;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rmj.appdriver.constants.RecordStatus;
import org.rmj.appdriver.GCrypt;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.iface.GEntity;
import org.rmj.appdriver.iface.GRecord;
import org.rmj.appdriver.MiscUtil;
import org.rmj.appdriver.SQLUtil;
import org.rmj.integsys.pojo.UnitClient;

/**
 *
 * @author kalyptus
 */
public class ClientInfo implements GRecord{
   private boolean pbWithParnt = false;
   private String psBranchCD = "";
   private String psUserIDxx = "";
   private String psWarnMsg = "";
   private String psErrMsgx = "";
   private GRider poGRider = null;

   public UnitClient newRecord() {
      UnitClient  loOcc = new UnitClient();
      Connection loCon = null;

      if(pbWithParnt){
         loCon = poGRider.getConnection();
         if(loCon == null)
            loCon = poGRider.doConnect();
      }
      else
         loCon = poGRider.doConnect();

//      if(poGRider.getConnection() == null)
//         loCon = poGRider.doConnect();
//      else
//         loCon = poGRider.getConnection();

      loOcc.setClientID(MiscUtil.getNextCode(loOcc.getTable(), "sClientID", false, loCon, ""));

      if(!pbWithParnt)
         MiscUtil.close(loCon);

      return loOcc;
   }

   public UnitClient openRecord(String fstransNox) {
      UnitClient  loOcc = new UnitClient();
      Connection loCon = null;

      if(pbWithParnt){
         loCon = poGRider.getConnection();
         if(loCon == null)
            loCon = poGRider.doConnect();
      }
      else
         loCon = poGRider.doConnect();

//      if(poGRider.getConnection() == null)
//         loCon = poGRider.doConnect();
//      else
//         loCon = poGRider.getConnection();

      //retrieve the record
      String lsSQL = MiscUtil.addCondition(getSQ_Master(), "sClientID = " + SQLUtil.toSQL(fstransNox));
      Statement loStmt = null;
      ResultSet loRS = null;
      try {
         loStmt = loCon.createStatement();
         loRS = loStmt.executeQuery(lsSQL);

         if(!loRS.next())
             setMessage("No Old Record Found!");
         else{
            //load each column to the entity
            for(int lnCol=1; lnCol<=loRS.getMetaData().getColumnCount(); lnCol++){
                loOcc.setValue(lnCol, loRS.getObject(lnCol));
            }
         }
      } catch (SQLException ex) {
         Logger.getLogger(ClientInfo.class.getName()).log(Level.SEVERE, null, ex);
         setErrMsg(ex.getMessage());
      }
      finally{
         MiscUtil.close(loRS);
         MiscUtil.close(loStmt);
         if(!pbWithParnt)
            MiscUtil.close(loCon);
      }

      return loOcc;
   }

   public UnitClient saveRecord(Object foEntity, String fsTransNox) {
      String lsSQL = "";
      UnitClient loOldEnt = null;
      UnitClient loNewEnt = null;
      UnitClient loResult = null;

      // Check for the value of foEntity
      if (!(foEntity instanceof UnitClient)) {
          setErrMsg("Invalid Entity Passed as Parameter");
          return loResult;
      }

      // Typecast the Entity to this object
      loNewEnt = (UnitClient) foEntity;

      //test for the validity of the different fields here
      if (loNewEnt.getLastName().equals("")) {
          setMessage("Invalid Last Name Detected");
          return loResult;
      }

      //test for the validity of the different fields here
      if (loNewEnt.getFirstName().equals("")) {
          setMessage("Invalid First Name Detected");
          return loResult;
      }

      //test for the validity of the different fields here
      if (loNewEnt.getTownID().equals("")) {
          setMessage("Invalid Town Detected");
          return loResult;
      }

      //TODO: Test the user rights in these area...

      //Set the value of sModified and dModified here
      //GCrypt loCrypt = new GCrypt();
      //loNewEnt.setModifiedBy(loCrypt.encrypt(psUserIDxx));
      loNewEnt.setModifiedBy(psUserIDxx);
      loNewEnt.setDateModified(poGRider.getServerDate());

      System.out.println(poGRider.getMessage() + getErrMsg() + poGRider.getServerDate());
      System.out.println("Date Modified: " + loNewEnt.getDateModified());

      //Generate the SQL Statement
      if (fsTransNox.equals("")) {
         //TODO: Get new id for this record...
         Connection loCon = null;
         if(pbWithParnt){
            loCon = poGRider.getConnection();
            if(loCon == null)
               loCon = poGRider.doConnect();
         }
         else
            loCon = poGRider.doConnect();

         loNewEnt.setValue(1, MiscUtil.getNextCode(loNewEnt.getTable(), "sClientID", false, loCon, ""));

         if(!pbWithParnt)
            MiscUtil.close(loCon);

         //Generate the INSERT statement
          lsSQL = MiscUtil.makeSQL((GEntity)loNewEnt);
      } else {
          //Reload previous record
          loOldEnt = openRecord(fsTransNox);
          //Generate the UPDATE statement
          lsSQL = MiscUtil.makeSQL((GEntity)loNewEnt, (GEntity)loOldEnt, "sClientID = " + SQLUtil.toSQL(loNewEnt.getValue(1)));
      }

      //No changes has been made
      if (lsSQL.equals("")) {
          setMessage("Record is not updated!");
          return loResult;
      }

      if(!pbWithParnt)
         poGRider.beginTrans();

      if(poGRider.executeQuery(lsSQL.toString(), loNewEnt.getTable(), "", "") == 0){
         if(!poGRider.getErrMsg().isEmpty())
            setErrMsg(poGRider.getErrMsg());
         else
            setMessage("No record updated");
      }
      else
         loResult = loNewEnt;

      if(!pbWithParnt){
         if(getErrMsg().isEmpty())
            poGRider.commitTrans();
         else
            poGRider.rollbackTrans();
      }

      return loResult;
   }

   public boolean deleteRecord(String fsTransNox) {
      UnitClient  loOcc = openRecord(fsTransNox);
      boolean lbResult = false;

      if(loOcc == null){
         setMessage("No record found!");
         return lbResult;
      }

      //TODO: Test the user rights in these area...

      StringBuilder lsSQL = new StringBuilder();
      lsSQL.append("DELETE FROM " + loOcc.getTable());
      lsSQL.append(" WHERE sClientID = " + SQLUtil.toSQL(fsTransNox));

      if(!pbWithParnt)
         poGRider.beginTrans();

      if(poGRider.executeQuery(lsSQL.toString(), loOcc.getTable(), "", "") == 0){
         if(!poGRider.getErrMsg().isEmpty())
            setErrMsg(poGRider.getErrMsg());
         else
            setMessage("No record deleted");
      }
      else
         lbResult = true;

      if(!pbWithParnt){
         if(getErrMsg().isEmpty())
            poGRider.commitTrans();
         else
            poGRider.rollbackTrans();
      }

      return lbResult;
   }

   public boolean deactivateRecord(String fsTransNox) {
      UnitClient  loOcc = openRecord(fsTransNox);
      boolean lbResult = false;

      if(loOcc == null){
         setMessage("No record found!");
         return lbResult;
      }

      if(loOcc.getRecdStat().equalsIgnoreCase(RecordStatus.INACTIVE)){
         setMessage("Current record is inactive!");
         return lbResult;
      }

      //TODO: Test the user rights in these area...

      //GCrypt loCrypt = new GCrypt();
      StringBuilder lsSQL = new StringBuilder();
      lsSQL.append("UPDATE " + loOcc.getTable() + " SET ");
      lsSQL.append("  cRecdStat = " + SQLUtil.toSQL(RecordStatus.INACTIVE));
      lsSQL.append(", sModified = " + SQLUtil.toSQL(psUserIDxx));
      lsSQL.append(", dModified = " + SQLUtil.toSQL(poGRider.getServerDate()));
      lsSQL.append(" WHERE sClientID = " + SQLUtil.toSQL(fsTransNox));

      if(!pbWithParnt)
         poGRider.beginTrans();

      if(poGRider.executeQuery(lsSQL.toString(), loOcc.getTable(), "", "") == 0){
         if(!poGRider.getErrMsg().isEmpty())
            setErrMsg(poGRider.getErrMsg());
         else
            setMessage("No record updated");
      }
      else
         lbResult = true;

      if(!pbWithParnt){
         if(getErrMsg().isEmpty())
            poGRider.commitTrans();
         else
            poGRider.rollbackTrans();
      }

      return lbResult;
   }

   public boolean activateRecord(String fsTransNox) {
      UnitClient  loOcc = openRecord(fsTransNox);
      boolean lbResult = false;

      if(loOcc == null){
         setMessage("No record found!");
         return lbResult;
      }

      if(loOcc.getRecdStat().equalsIgnoreCase(RecordStatus.ACTIVE)){
         setMessage("Current record is active!");
         return lbResult;
      }

      //TODO: Test the user rights in these area...

      //GCrypt loCrypt = new GCrypt();
      StringBuilder lsSQL = new StringBuilder();
      lsSQL.append("UPDATE " + loOcc.getTable() + " SET ");
      lsSQL.append("  cRecdStat = " + SQLUtil.toSQL(RecordStatus.ACTIVE));
      lsSQL.append(", sModified = " + SQLUtil.toSQL(psUserIDxx));
      lsSQL.append(", dModified = " + SQLUtil.toSQL(poGRider.getServerDate()));
      lsSQL.append(" WHERE sClientID = " + SQLUtil.toSQL(fsTransNox));

      if(!pbWithParnt)
         poGRider.beginTrans();

      if(poGRider.executeQuery(lsSQL.toString(), loOcc.getTable(), "", "") == 0){
         if(!poGRider.getErrMsg().isEmpty())
            setErrMsg(poGRider.getErrMsg());
         else
            setMessage("No record updated");
      }
      else
         lbResult = true;

      if(!pbWithParnt){
         if(getErrMsg().isEmpty())
            poGRider.commitTrans();
         else
            poGRider.rollbackTrans();
      }

      return lbResult;
   }

   public String getMessage() {
      return psWarnMsg;
   }

   public void setMessage(String fsMessage) {
      this.psWarnMsg = fsMessage;
   }

   public String getErrMsg() {
      return psErrMsgx;
   }

   public void setErrMsg(String fsErrMsg) {
      this.psErrMsgx = fsErrMsg;
   }

   public void setBranch(String foBranchCD) {
      this.psBranchCD = foBranchCD;
   }

   public void setWithParent(boolean fbWithParent) {
      this.pbWithParnt = fbWithParent;
   }

   public String getSQ_Master() {
      return (MiscUtil.makeSelect(new UnitClient()));
   }

   // Added methods here
   public void setGRider(GRider foGRider) {
      this.poGRider = foGRider;
      this.psUserIDxx = foGRider.getUserID();
      if(psBranchCD.isEmpty())
         psBranchCD = poGRider.getBranchCode();
   }

   public static int xeLR_APPLICANT = 0;
   public static int xeLR_SPOUSE = 1;
   public static int xeMC_CUSTOMER = 2;
   public static int xeGC_CUSTOMER = 3;
   public static int xeLR_COMAKER = 4;
   public static int xeLR_FINANCER = 5;
   public static int xeLR_DEPENDENT = 6;
   public static int xeSP_JOBORDER = 7;
   public static int xeSP_SALES = 8;
   public static int xeCP_SALES = 9;

   //Possible values for cLRClient, cMCClient, cSCClient, cSPClient, cCPClient
   //0-> !Customer
   //1-> Customer
   //2-> Extended
   //3-> Potential
}
