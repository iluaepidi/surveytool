package ilu.surveytool.databasemanager.constants;

public class DBSQLQueries {

	//Selects
		//Content
		public final static String s_SELECT_CONTENT_BY_LANGUAGE_CONTENTTYPE = "SELECT c.text FROM surveytool.content c "
				+ "INNER JOIN surveytool.language l ON c.idLanguage = l.idLanguage "
				+ "INNER JOIN surveytool.contenttype ct ON c.idContentType = ct.idContentType "
				+ "WHERE l.isoName = ? and ct.name = ?";
		
		//page
		public final static String s_SELECT_PAGE_ID_BY_QUESTIONNAIRE_ID = "SELECT idPage FROM surveytool.questionnaire q "
		+ "inner join surveytool.forma f on q.idQuestionnaire = f.idQuestionnaire "
		+ "inner join surveytool.page p on f.idForma = p.idForma "
		+ "where q.idQuestionnaire = ?";
		
		//project
		public final static String s_SELECT_PROJECT_BY_NAME = "SELECT * FROM surveytool.project where projectName = ?";
		
		//Question Type
		public final static String s_SELECT_QUESTIONTYPE_TEMPLATE_FILE_BY_ID = "SELECT templateFile FROM surveytool.questiontype where name = ?";
		
		//User
		public final static String s_SELECT_LOGIN = "SELECT * FROM surveytool.user u inner join surveytool.rol r on r.idRol = u.idRol WHERE (userName = ? or email = ?) and password = ?";
		
		//Questionnaire
		public final static String s_SELECT_QUESTIONNAIRE = "SELECT * FROM surveytool.questionnaire q INNER JOIN surveytool.project p ON q.idProject = p.idProject WHERE q.author = ?";
		public final static String s_SELECT_QUESTIONNAIRE_TABLE_INFO = "SELECT q.idQuestionnaire, q.deadLineDate, c.text title, "
				+ "(select count(*) FROM surveytool.userquestionnaire auq where auq.idQuestionnaire = q.idQuestionnaire) allUsers, "
				+ "(select count(*) FROM surveytool.userquestionnaire fuq WHERE fuq.idQuestionnaire = q.idQuestionnaire and fuq.state = ?) usersFinished "
						+ "FROM surveytool.questionnaire q "
						+ "INNER JOIN surveytool.content c ON q.idContent = c.idContent "
						+ "INNER JOIN surveytool.language l ON c.idLanguage = l.idLanguage "
						+ "INNER JOIN surveytool.contenttype ct ON c.idContentType = ct.idContentType "
						+ "WHERE q.author = ? and l.isoName = ? and ct.name = ? ";
		public final static String s_SELECT_QUESTIONNAIRE_BY_ID = "SELECT p.projectName, ct.name contentTypeName, c.text, l.isoName FROM surveytool.questionnaire q "
				+ "inner join surveytool.project p on p.idProject = q.idProject "
				+ "inner join surveytool.content c on c.idContent = q.idContent "
				+ "inner join surveytool.contenttype ct on c.idContentType = ct.idContentType "
				+ "inner join surveytool.language l on l.idLanguage = c.idLanguage "
				+ "where q.idQuestionnaire = ?";
		
		//User Questionnaire
		public final static String s_SELECT_USERS_BY_QUESTIONNAIREID = "SELECT count(*) FROM surveytool.userquestionnaire WHERE idQuestionnaire = ?";
		public final static String s_SELECT_USERS_BY_QUESTIONNAIREID_FINISHED = "SELECT count(*) FROM surveytool.userquestionnaire WHERE idQuestionnaire = ? and state = ?";
		
	//inserts
		//content
			public final static String s_INSERT_CONTENT = "INSERT INTO `surveytool`.`content` (`idContent`, `idLanguage`, `idContentType`, `text`) VALUES (?, "
					+ "(SELECT idLanguage FROM surveytool.language WHERE isoName = ?), "
					+ "(SELECT idContentType FROM surveytool.contenttype WHERE name = ?), ?)";
		//contentIndex
			public final static String s_INSERT_CONTENT_INDEX = "INSERT INTO `surveytool`.`contentindex` (`idContent`) VALUES (null);";
		//forma
			public final static String s_INSERT_FORMA = "INSERT INTO `surveytool`.`forma` (`idQuestionnaire`) VALUES (?)";
		//page
			public final static String s_INSERT_PAGE = "INSERT INTO `surveytool`.`page` (`idForma`, `numPage`) VALUES (?, ?)";
		//project	
			public final static String s_INSERT_PROJECT = "INSERT INTO `surveytool`.`project` (`projectName`) VALUES (?)";

		//Question
			public final static String s_INSERT_QUESTION = "INSERT INTO `surveytool`.`question` (`tag`, `idQuestionType`, `idContent`, `idCategory`, `mainVersion`) VALUES (?, "
				+ "(select idQuestionType from surveytool.questiontype where name = ?), ?, "
				+ "(select idCategory from surveytool.category where name = ?), "
				+ "(select idLanguage from surveytool.language where isoName = ?))";
		//QuestionByPage
			public final static String s_INSERT_QUESTION_BY_PAGE = "INSERT INTO `surveytool`.`questionbypage` (`idPage`, `idQuestion`, `numQuestion`, `mandatory`) VALUES (?, ?, ?, ?)";
		//Questionnaire
			public final static String s_INSERT_QUESTIONNAIRE = "INSERT INTO `surveytool`.`questionnaire` (`state`, `idContent`, `idProject`, `publicId`, `author`) VALUES (?, ?, ?, ?, ?)";
			
		
			
		
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
