package ilu.surveytool.databasemanager.constants;

public class DBSQLQueries {

	//Selects
		//Content
		public final static String s_SELECT_CONTENT_BY_ID_LANGUAGE = "SELECT c.idContent, ct.name contentTypeName, l.isoName, c.text FROM surveytool.content c "
				+ "inner join surveytool.contenttype ct on c.idContentType = ct.idContentType "
				+ "inner join surveytool.language l on c.idLanguage = l.idLanguage "
				+ "where c.idContent = ? and l.isoName = ?";
		public final static String s_SELECT_CONTENT_BY_ID_LANGUAGE_CONTENTTYPE = "SELECT c.idContent, ct.name contentTypeName, l.isoName, c.text FROM surveytool.content c "
				+ "inner join surveytool.contenttype ct on c.idContentType = ct.idContentType "
				+ "inner join surveytool.language l on c.idLanguage = l.idLanguage "
				+ "where c.idContent = ? and l.isoName = ? and ct.name = ?";
		public final static String s_SELECT_CONTENT_BY_LANGUAGE_CONTENTTYPE = "SELECT c.text FROM surveytool.content c "
				+ "INNER JOIN surveytool.language l ON c.idLanguage = l.idLanguage "
				+ "INNER JOIN surveytool.contenttype ct ON c.idContentType = ct.idContentType "
				+ "WHERE l.isoName = ? and ct.name = ?";
		
		//option
		public final static String s_SELECT_OPTION_BY_OPTIONID = "SELECT * FROM surveytool.`option` where idOption = ?";
		public final static String s_SELECT_OPTION_BY_OPTIONSGROUPID = "SELECT o.idOption, o.idContent, obg.index FROM surveytool.optionsbygroup obg "
				+ "inner join surveytool.option o on obg.idOption = o.idOption "
				+ "where obg.idOptionsGroup = ? "
				+ "order by obg.index";
		//optionsGroup
		public final static String s_SELECT_OPTIONSGROUP_BY_ID = "select * from surveytool.optionsgroup where idOptionsGroup = ?";
		public final static String s_SELECT_OPTIONSGROUP_BY_QUESTION_ID = "SELECT og.*, ot.name optionTypeName FROM surveytool.optionsgroup og "
				+ "inner join surveytool.optiontype ot on og.idOptionType = ot.idOptionType "
				+ "where idQuestion = ?";
		
		//optionsByGroup
		public final static String s_SELECT_OPTIONSBYGROUP_BY_ID = "SELECT * FROM surveytool.optionsbygroup where idOptionsGroup = ? "
				+ "order by 'index'";
		public final static String s_SELECT_OPTIONSBYGROUP_ID_BY_OPTIONID = "SELECT idOptionsGroup FROM surveytool.optionsbygroup where idOption = ?";
		
		//page
		public final static String s_SELECT_PAGE_ID_BY_QUESTIONNAIRE_ID = "SELECT idPage FROM surveytool.questionnaire q "
				+ "inner join surveytool.forma f on q.idQuestionnaire = f.idQuestionnaire "
				+ "inner join surveytool.page p on f.idForma = p.idForma "
				+ "where q.idQuestionnaire = ?";
		
		//poll
		public final static String s_SELECT_POLL_TABLE_INFO = "SELECT p.idPoll, p.deadLineDate, p.publicId, c.text title "
						+ "FROM surveytool.poll p "
						+ "INNER JOIN surveytool.content c ON p.idContent = c.idContent "
						+ "INNER JOIN surveytool.language l ON c.idLanguage = l.idLanguage "
						+ "INNER JOIN surveytool.contenttype ct ON c.idContentType = ct.idContentType "
						+ "WHERE p.author = ? and l.isoName = ? and ct.name = ? ";
		public final static String s_SELECT_POLL_BY_PUBLIC_ID = "SELECT *, pr.projectName FROM surveytool.poll p "
				+ "inner join surveytool.project pr on p.idProject = pr.idProject "
				+ "where publicId = ?";
		public final static String s_SELECT_POLL_CONTENTID = "SELECT idContent FROM surveytool.poll where idPoll = ?";
		public final static String s_SELECT_POLL_BY_ID = "SELECT *, pr.projectName FROM surveytool.poll p "
				+ "inner join surveytool.project pr on p.idProject = pr.idProject "
				+ "where p.idPoll = ?";
		
		
		//project
		public final static String s_SELECT_PROJECT_BY_NAME = "SELECT * FROM surveytool.project where projectName = ?";
		
		//Question Type
		public final static String s_SELECT_QUESTIONTYPE_TEMPLATE_FILE_BY_ID = "SELECT templateFile FROM surveytool.questiontype where name = ?";
		
		//resources
		public final static String s_SELECT_RESOURCES_BY_QUESTIONID = "SELECT r.*, rt.name resourceTypeName FROM surveytool.resoruces r "
				+ "inner join surveytool.questionresource qr on r.idResoruces = qr.idResoruces "
				+ "inner join surveytool.resourcetype rt on r.idResourceType = rt.idResourceType "
				+ "where qr.idQuestion = ?";
		public final static String s_SELECT_RESOURCE_BY_ID = "select r.*, rt.name resourceTypeName from surveytool.resoruces r "
				+ "inner join surveytool.resourcetype rt on r.idResourceType = rt.idResourceType "
				+ "where idResoruces = ?";
		
		//User
		public final static String s_SELECT_LOGIN = "SELECT * FROM surveytool.user u inner join surveytool.rol r on r.idRol = u.idRol WHERE (userName = ? or email = ?) and password = ?";
		public final static String s_SELECT_USER_EMAIL_BY_USERID = "SELECT email FROM surveytool.user where idUser = ?";
		
		//Question
		public final static String s_SELECT_QUESTION_BY_SURVEYID = "SELECT q.*, qt.name questionTypeName, qt.templateFile, qt.formFile, c.name categoryName, qp.mandatory, l.isoName FROM surveytool.questionnaire s "
				+ "inner join surveytool.forma f on f.idQuestionnaire = s.idQuestionnaire "
				+ "inner join surveytool.page p on f.idForma = p.idForma "
				+ "inner join surveytool.questionbypage qp on qp.idPage = p.idPage "
				+ "inner join surveytool.question q on q.idQuestion = qp.idQuestion "
				+ "inner join surveytool.questiontype qt on q.idQuestionType = qt.idQuestionType "
				+ "inner join surveytool.category c on q.idCategory = c.idCategory "
				+ "inner join surveytool.language l on q.mainVersion = l.idLanguage "
				+ "where s.idQuestionnaire = ?";
		public final static String s_SELECT_QUESTION_CONTENT_BY_QUESTIONID = "SELECT ct.name questionTypeName, c.text FROM surveytool.question q "
				+ "inner join surveytool.content c on q.idContent = c.idContent "
				+ "inner join surveytool.language l on c.idLanguage = l.idLanguage "
				+ "inner join surveytool.contenttype ct on c.idContentType = ct.idContentType "
				+ "where q.idQuestion = ? and l.isoName = ?";
		public final static String s_SELECT_QUESTION_CONTENTID_BY_QUESTIONID = "SELECT idContent FROM surveytool.question where idQuestion = ?";
		public final static String s_SELECT_QUESTION_BY_POLLID = "SELECT q.*, qt.name questionTypeName, qt.templateFile, qt.formFile, c.name categoryName, l.isoName FROM surveytool.questionbypoll qbp "
				+ "inner join surveytool.question q on qbp.idQuestion = q.idQuestion "
				+ "inner join surveytool.questiontype qt on q.idQuestionType = qt.idQuestionType "
				+ "inner join surveytool.category c on q.idCategory = c.idCategory "
				+ "inner join surveytool.language l on q.mainVersion = l.idLanguage "
				+ "where qbp.idPoll = ?";
		
		//Questionnaire
		public final static String s_SELECT_QUESTIONNAIRE = "SELECT * FROM surveytool.questionnaire q INNER JOIN surveytool.project p ON q.idProject = p.idProject WHERE q.author = ?";
		public final static String s_SELECT_QUESTIONNAIRE_PUBLICID = "SELECT publicId FROM surveytool.questionnaire WHERE idQuestionnaire = ?";
		public final static String s_SELECT_QUESTIONNAIRE_TABLE_INFO = "SELECT q.idQuestionnaire, q.deadLineDate, c.text title, "
				+ "(select count(*) FROM surveytool.userquestionnaire auq where auq.idQuestionnaire = q.idQuestionnaire) allUsers, "
				+ "(select count(*) FROM surveytool.userquestionnaire fuq WHERE fuq.idQuestionnaire = q.idQuestionnaire and fuq.state = ?) usersFinished "
						+ "FROM surveytool.questionnaire q "
						+ "INNER JOIN surveytool.content c ON q.idContent = c.idContent "
						+ "INNER JOIN surveytool.language l ON c.idLanguage = l.idLanguage "
						+ "INNER JOIN surveytool.contenttype ct ON c.idContentType = ct.idContentType "
						+ "WHERE q.author = ? and l.isoName = ? and ct.name = ? ";
		public final static String s_SELECT_QUESTIONNAIRE_BY_ID = "SELECT p.projectName, ct.name contentTypeName, c.text, l.isoName, q.publicId, q.author FROM surveytool.questionnaire q "
				+ "inner join surveytool.project p on p.idProject = q.idProject "
				+ "inner join surveytool.content c on c.idContent = q.idContent "
				+ "inner join surveytool.contenttype ct on c.idContentType = ct.idContentType "
				+ "inner join surveytool.language l on l.idLanguage = c.idLanguage "
				+ "where q.idQuestionnaire = ?";
		public final static String s_SELECT_QUESTIONNAIRE_BY_PUBLIC_ID = "SELECT q.*, p.projectName FROM surveytool.questionnaire q "
				+ "inner join surveytool.project p on p.idProject = q.idProject "
				+ "where publicId = ?";
		public final static String s_SELECT_QUESTIONNAIRE_CONTENTID = "SELECT idContent FROM surveytool.questionnaire WHERE idQuestionnaire = ?";
		public final static String s_SELECT_QUESTIONNAIRE_PROJECTID = "SELECT idProject FROM surveytool.questionnaire WHERE idQuestionnaire = ?";
		public final static String s_SELECT_QUESTIONNAIRESID_BY_PROJECTID = "SELECT idQuestionnaire FROM surveytool.questionnaire WHERE idProject = ?";
		
		//QuestionByPage
		public final static String s_SELECT_QUESTIONBYPAGE_MANDATORY = "SELECT mandatory FROM surveytool.questionbypage where idQuestion = ? and idPage = ?";
		
		//User Questionnaire
		public final static String s_SELECT_USERS_BY_QUESTIONNAIREID = "SELECT count(*) FROM surveytool.userquestionnaire WHERE idQuestionnaire = ?";
		public final static String s_SELECT_USERS_BY_QUESTIONNAIREID_FINISHED = "SELECT count(*) FROM surveytool.userquestionnaire WHERE idQuestionnaire = ? and state = ?";
		
	//inserts
		//AnonimousUser
			public final static String s_INSERT_ANONIMOUS_USER = "INSERT INTO `surveytool`.`anonimoususer` (`idQuestionnaire`) VALUES (?)";
		//AnonimousResponse
			public final static String s_INSERT_ANONIMOUS_RESPONSE = "INSERT INTO `surveytool`.`anonimousresponse` (`idAnonimousUser`, `idResponse`) VALUES (?, ?)";
		//content
			public final static String s_INSERT_CONTENT = "INSERT INTO `surveytool`.`content` (`idContent`, `idLanguage`, `idContentType`, `text`) VALUES (?, "
					+ "(SELECT idLanguage FROM surveytool.language WHERE isoName = ?), "
					+ "(SELECT idContentType FROM surveytool.contenttype WHERE name = ?), ?)";
		//contentIndex
			public final static String s_INSERT_CONTENT_INDEX = "INSERT INTO `surveytool`.`contentindex` (`idContent`) VALUES (null);";
		//forma
			public final static String s_INSERT_FORMA = "INSERT INTO `surveytool`.`forma` (`idQuestionnaire`) VALUES (?)";
		//Option
			public final static String s_INSERT_OPTION = "INSERT INTO `surveytool`.`option` (`idContent`) VALUES (?)";
		//OptionByGroup
			public final static String s_INSERT_OPTIONS_BY_GROUP = "INSERT INTO `surveytool`.`optionsbygroup` (`idOptionsGroup`, `idOption`, `index`) VALUES ( ?, ?, ?)";
		//OptionGroup
			public final static String s_INSERT_OPTIONS_GROUP = "INSERT INTO `surveytool`.`optionsgroup` (`idQuestion`, `idContent`, `idOptionType`) VALUES (?, ?, "
					+ "(SELECT idOptionType FROM surveytool.optiontype where name = ?))";
		//poll
			public final static String s_INSERT_POLL = "INSERT INTO `surveytool`.`poll` (`publicId`, `author`, `idQuestionnaire`, `idContent`, `idProject`, `callUrl`) VALUES (?, ?, ?, ?, ?, ?)";
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
		//QuestionByPoll
			public final static String s_INSERT_QUESTION_BY_POLL = "INSERT INTO `surveytool`.`questionbypoll` (`idPoll`, `idQuestion`, `index`) VALUES (?, ?, ?)";
		//QuestionResource
			public final static String s_INSERT_QUESTION_RESOURCE = "INSERT INTO `surveytool`.`questionresource` (`idQuestion`, `idResoruces`) VALUES (?, ?)";
		//Questionnaire
			public final static String s_INSERT_QUESTIONNAIRE = "INSERT INTO `surveytool`.`questionnaire` (`state`, `idContent`, `idProject`, `publicId`, `author`) VALUES (?, ?, ?, ?, ?)";
		//Resource
			public final static String s_INSERT_RESOURCE = "INSERT INTO `surveytool`.`resoruces` (`idResourceType`, `urlPath`, `idContent`) VALUES ((SELECT idResourceType FROM surveytool.resourcetype where name = ?), ?, ?)";
		//Responses
			public final static String s_INSERT_RESPONSE = "INSERT INTO `surveytool`.`responses` (`idQuestion`, `idOptionsGroup`, `value`, `idPoll`) VALUES (?, ?, ?, ?)";
			
	//updates
		//content
			public final static String s_UPDATE_CONTENT_TEXT = "UPDATE `surveytool`.`content` SET `text`= ? "
					+ "WHERE `idContent`= ? "
					+ "and`idLanguage`= (SELECT idLanguage FROM surveytool.language where isoName = ?) "
					+ "and`idContentType`= (SELECT idContentType FROM surveytool.contenttype where name = ?)";
		//optionsByCroup
			public final static String s_UPDATE_OPTIONSBYGROUP_INDEX = "UPDATE `surveytool`.`optionsbygroup` SET `index`=? WHERE `idOptionsGroup`=? and`idOption`=?";
		//project
			public final static String s_UPDATE_PROJECT_NAME = "UPDATE surveytool.project SET projectName=? WHERE idProject=?";
		//questionByPage
			public final static String s_UPDATE_QUESTIONBYPAGE_MANDATORY = "UPDATE surveytool.questionbypage SET mandatory=? WHERE idPage=? and idQuestion=?";
		//questionnaire
			public final static String s_UPDATE_QUESTIONNAIRE_PROJECT = "UPDATE surveytool.questionnaire SET idProject=? WHERE idQuestionnaire= ?";
		//resources
			public final static String s_UPDATE_RESOURCE_URLPATH = "UPDATE `surveytool`.`resoruces` SET `urlPath`=? WHERE `idResoruces`=?";
		
	//delete
		//contentIndex
			public final static String s_DELETE_CONTENT = "DELETE FROM `surveytool`.`contentindex` WHERE `idContent`=?";
			
		//option
			public final static String s_DELETE_OPTION = "DELETE FROM surveytool.option WHERE idOption = ?";
			
		//questionByPage
			public final static String s_DELETE_QUESTION_BY_PAGE = "DELETE FROM surveytool.questionbypage WHERE idPage=? and idQuestion=?";
			
		//resources
			public final static String s_DELETE_RESOURCE = "DELETE FROM `surveytool`.`resoruces` WHERE `idResoruces`=?";
		
}
