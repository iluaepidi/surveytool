package ilu.surveytool.databasemanager.constants;

public class DBSQLQueries {

	//Selects
		/*
		//language
		public final static String s_SELECT_CONTENTTYPE_ALL_ORDERBY_CONTENTTYPEID = "Select * from personaemgrlacaixa.contenttype order by contentTypeId";
		//multilanguagecontent
		public final static String s_SELECT_MULTILANGUAGECONTENT_TEXT_BY_CONTENTID_LANGUAGENAME_CONTENTTYPENAME = "SELECT text FROM personaemgrlacaixa.multilanguagecontent as mlc " + 
				"INNER JOIN personaemgrlacaixa.language as l ON  l.language_id = mlc.languageId " + 
				"INNER JOIN personaemgrlacaixa.contenttype as ct ON mlc.contentTypeId = ct.contentTypeId " +
				"where mlc.contentId = ? and l.isoName = ? and ct.name = ?";
		public final static String s_SELECT_USER_FROM_USER_PROFILE_SENT = "SELECT distinct u.* FROM personaemgrlacaixa.userprofilesent as ups INNER JOIN personaemgrlacaixa.user as u on u.userId = ups.userId";
		*/
		
		
	//updates
		/*
		public final static String s_UPDATE_USER_PREVIEW_AND_REQUESTDATE = "UPDATE user SET preview = ?, cardRequestDate = ? WHERE userId =?";
		public final static String s_UPDATE_MULTILANGUAGECONTENT = "UPDATE `personaemgrlacaixa`.`multilanguagecontent` SET `text`= ? WHERE `contentId`=? and`languageId`=? and`contentTypeId`=?";
		*/
		
	//inserts
		/*
		public final static String s_INSERT_USER_PREPROFILES = "INSERT INTO userpreprofile (`userId`, `preprofileId`) VALUES (?, ?)";
		*/
		
	//delete
		/*
		public final static String s_DELETE_USER_OPTION_VALUE_BY_USERID_OPTIONID = "DELETE FROM useroptionvalue WHERE userId = ? and optionId = ?";
		*/
		
}
