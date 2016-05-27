package ilu.surveytool.databasemanager.constants;

public class DBSQLQueries {

	//Selects
		//AnonymousResponse
		public final static String s_SELECT_ANONYMOUS_RESPONSE_BY_SURVEY_ID = "SELECT au.idAnonimousUser, r.idQuestion, r.idOptionsGroup, "
					+ "if(qt.name = 'simple' or qt.name = 'multiple' or qt.name = 'matrix', "
					+ "(SELECT c.text FROM surveytool.`option` as o "
					+ "inner join surveytool.content as c on o.idContent = c.idContent "
					+ "inner join surveytool.contenttype as ct on c.idContentType = ct.idContentType "
					+ "where ct.name = 'title' and o.idOption = cast(r.value as unsigned)), r.value) value "
				+ "FROM surveytool.anonimoususer as au "
				+ "inner join surveytool.anonimousresponse as ar on au.idAnonimousUser = ar.idAnonimousUser "
				+ "inner join surveytool.responses as r on ar.idResponse = r.idResponse "
				+ "inner join surveytool.question as q on r.idQuestion = q.idQuestion "
				+ "inner join surveytool.questiontype as qt on q.idQuestionType = qt.idQuestionType "
				+ "where idQuestionnaire = ? order by idAnonimousUser";
		public final static String s_SELECT_ANONYMOUS_RESPONSE_BY_POLL_ID = "SELECT au.idAnonimousUser, r.idQuestion, "
					+ "if(qt.name = 'simple', "
					+ "(SELECT c.text FROM surveytool.`option` as o "
					+ "inner join surveytool.content as c on o.idContent = c.idContent "
					+ "inner join surveytool.contenttype as ct on c.idContentType = ct.idContentType "
					+ "where ct.name = 'title' and o.idOption = cast(r.value as unsigned)), r.value) value "
				+ "FROM surveytool.anonimoususer as au "
				+ "inner join surveytool.anonimousresponse as ar on au.idAnonimousUser = ar.idAnonimousUser "
				+ "inner join surveytool.responses as r on ar.idResponse = r.idResponse "
				+ "inner join surveytool.question as q on r.idQuestion = q.idQuestion "
				+ "inner join surveytool.questiontype as qt on q.idQuestionType = qt.idQuestionType "
				+ "where r.idPoll = ? order by au.idAnonimousUser";
	
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
		public final static String s_SELECT_OPTIONID_BY_QUESTIONID = "SELECT o.idOption FROM surveytool.optionsbygroup obg "
				+ "inner join surveytool.option o on obg.idOption = o.idOption "
				+ "inner join surveytool.optionsgroup og on obg.idOptionsGroup = og.idOptionsGroup "
				+ "where og.idQuestion = ? ";
		//optionsGroup
		public final static String s_SELECT_OPTIONSGROUP_BY_ID = "select * from surveytool.optionsgroup where idOptionsGroup = ?";
		public final static String s_SELECT_OPTIONSGROUP_BY_QUESTION_ID = "SELECT og.*, ot.name optionTypeName FROM surveytool.optionsgroup og "
				+ "inner join surveytool.optiontype ot on og.idOptionType = ot.idOptionType "
				+ "where og.idQuestion = ? order by og.index";
		public final static String s_SELECT_OPTIONSGROUPID_BY_QUESTION_ID = "SELECT og.idOptionsGroup FROM surveytool.optionsgroup og "
				+ "where og.idQuestion = ? order by og.index";
		
		//optionsByGroup
		public final static String s_SELECT_OPTIONSBYGROUP_BY_ID = "SELECT obg.* FROM surveytool.optionsbygroup obg where obg.idOptionsGroup = ? "
				+ "order by obg.index";
		public final static String s_SELECT_OPTIONSBYGROUP_ID_BY_OPTIONID = "SELECT idOptionsGroup FROM surveytool.optionsbygroup where idOption = ?";
		
		//page
		public final static String s_SELECT_PAGE_ID_BY_QUESTIONNAIRE_ID = "SELECT p.idPage FROM surveytool.questionnaire q "
				+ "inner join surveytool.forma f on q.idQuestionnaire = f.idQuestionnaire "
				+ "inner join surveytool.section s on s.idForma = f.idForma "
				+ "inner join surveytool.page p on s.idSection = p.idSection "
				+ "where q.idQuestionnaire = ?";
		public final static String s_SELECT_PAGES_BY_SECTIONID = "SELECT * FROM surveytool.page where idSection = ? order by numPage";
		public final static String s_SELECT_PAGES_ID_BY_SECTIONID = "SELECT * FROM surveytool.page where idSection = ? order by numPage desc";
		public final static String s_SELECT_PAGE_BY_PAGEID = "SELECT * FROM surveytool.page where idPage = ?";
		public final static String s_SELECT_NUM_PAGE_BY_SECTIONID = "SELECT count(*) " + DBFieldNames.s_NUM_ELEMENTS + " FROM surveytool.page where idSection = ?";
		
		//poll
		public final static String s_SELECT_POLL_TABLE_INFO = "SELECT p.idPoll, p.deadLineDate, p.publicId, c.text title, (SELECT count(*) FROM surveytool.responses where idPoll = p.idPoll) numResp  "
				+ "FROM surveytool.poll p "
				+ "INNER JOIN surveytool.content c ON p.idContent = c.idContent "
				+ "INNER JOIN surveytool.language l ON c.idLanguage = l.idLanguage "
				+ "INNER JOIN surveytool.contenttype ct ON c.idContentType = ct.idContentType "
				+ "WHERE p.author = ? and l.isoName = ? and ct.name = ? ";
		public final static String s_SELECT_POLL_TABLE_INFO_BY_ID = "SELECT p.idPoll, p.deadLineDate, p.publicId, c.text title "
				+ "FROM surveytool.poll p "
				+ "INNER JOIN surveytool.content c ON p.idContent = c.idContent "
				+ "INNER JOIN surveytool.language l ON c.idLanguage = l.idLanguage "
				+ "INNER JOIN surveytool.contenttype ct ON c.idContentType = ct.idContentType "
				+ "WHERE p.idPoll = ? and l.isoName = ? and ct.name = ? ";
		public final static String s_SELECT_POLL_BY_PUBLIC_ID = "SELECT *, pr.projectName FROM surveytool.poll p "
				+ "inner join surveytool.project pr on p.idProject = pr.idProject "
				+ "where publicId = ?";
		public final static String s_SELECT_POLL_CONTENTID = "SELECT idContent FROM surveytool.poll where idPoll = ?";
		public final static String s_SELECT_POLL_BY_ID = "SELECT *, pr.projectName FROM surveytool.poll p "
				+ "inner join surveytool.project pr on p.idProject = pr.idProject "
				+ "where p.idPoll = ?";
		
		//poll response
		public final static String s_SELECT_POLL_RESPONSES_RESUME = "SELECT o.idOption, c.text, (SELECT count(*) FROM surveytool.responses where value = o.idOption) numResp "
				+ "FROM surveytool.questionbypoll qbp "
				+ "inner join surveytool.optionsgroup og on qbp.idQuestion = og.idQuestion "
				+ "inner join surveytool.optionsbygroup obg on og.idOptionsGroup = obg.idOptionsGroup "
				+ "inner join surveytool.`option` o on obg.idOption = o.idOption "
				+ "inner join surveytool.content c on o.idContent = c.idContent "
				+ "inner join surveytool.language l on c.idLanguage = l.idLanguage "
				+ "inner join surveytool.contenttype ct on c.idContentType = ct.idContentType "
				+ "where qbp.idPoll = ? and l.isoName = ? and ct.name = 'title' "
				+ "order by numResp desc";
		
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
		public final static String s_SELECT_LOGIN = "SELECT * FROM surveytool.user u inner join surveytool.rol r on r.idRol = u.idRol inner join surveytool.language l on l.idLanguage = u.idLanguage WHERE (userName = ? or email = ?) and password = ?";
		public final static String s_SELECT_USER_EMAIL_BY_USERID = "SELECT email FROM surveytool.user where idUser = ?";
		public final static String s_SELECT_CHECK_EXISTS_USER_BY_USERNAME = "SELECT idUser FROM surveytool.user WHERE userName LIKE ?";
		public final static String s_SELECT_CHECK_EXISTS_USER_BY_EMAIL = "SELECT idUser FROM surveytool.user WHERE email LIKE ?";
		public final static String s_SELECT_CHECK_EXISTS_USER_BY_EMAIL_PROFILE = "SELECT idUser FROM surveytool.user WHERE email LIKE ? AND userName != ?";
		public final static String s_GET_IDLANGUEGE_FROM_ISONAME = "SELECT idLanguage FROM surveytool.language WHERE isoName LIKE ?";
		public final static String s_GET_ISOLANGUEGE_FROM_IDLANGUAGE = "SELECT isoName FROM surveytool.language WHERE idLanguage = ?";
		public final static String s_SELECT_LIST_LANGUAGES = "SELECT name,isoName FROM surveytool.language";
				
		//Question
		public final static String s_SELECT_QUESTION_BY_SURVEYID = "SELECT q.*, qp.index, qt.name questionTypeName, qt.templateFile, qt.formFile, c.name categoryName, qp.mandatory, qp.optionalAnswer, qp.idPage "
				+ "FROM surveytool.questionnaire s "
				+ "inner join surveytool.forma f on f.idQuestionnaire = s.idQuestionnaire "
				+ "inner join surveytool.section sc on sc.idForma = f.idForma "
				+ "inner join surveytool.page p on sc.idSection = p.idSection "
				+ "inner join surveytool.questionbypage qp on qp.idPage = p.idPage "
				+ "inner join surveytool.question q on q.idQuestion = qp.idQuestion "
				+ "inner join surveytool.questiontype qt on q.idQuestionType = qt.idQuestionType "
				+ "inner join surveytool.category c on q.idCategory = c.idCategory "
				+ "where s.idQuestionnaire = ? order by qp.index";
		public final static String s_SELECT_QUESTION_BY_SECTIONID = "SELECT q.*, qp.`index`, qt.name questionTypeName, qt.templateFile, qt.formFile, c.name categoryName, qp.mandatory, qp.optionalAnswer, qp.idPage "
				+ "FROM surveytool.section sc "
				+ "inner join surveytool.page p on sc.idSection = p.idSection "
				+ "inner join surveytool.questionbypage qp on qp.idPage = p.idPage "
				+ "inner join surveytool.question q on q.idQuestion = qp.idQuestion "
				+ "inner join surveytool.questiontype qt on q.idQuestionType = qt.idQuestionType "
				+ "inner join surveytool.category c on q.idCategory = c.idCategory "
				+ "where sc.idSection = ? order by qp.`index`";
		public final static String s_SELECT_QUESTION_BY_PAGEID = "SELECT q.*, qp.`index`, qt.name questionTypeName, qt.templateFile, qt.formFile, c.name categoryName, qp.mandatory, qp.optionalAnswer, qp.idPage "
				+ "FROM surveytool.page p "
				+ "inner join surveytool.questionbypage qp on qp.idPage = p.idPage "
				+ "inner join surveytool.question q on q.idQuestion = qp.idQuestion "
				+ "inner join surveytool.questiontype qt on q.idQuestionType = qt.idQuestionType "
				+ "inner join surveytool.category c on q.idCategory = c.idCategory "
				+ "where p.idPage = ? order by qp.`index`";
		public final static String s_SELECT_QUESTION_CONTENT_BY_QUESTIONID = "SELECT ct.name questionTypeName, c.text FROM surveytool.question q "
				+ "inner join surveytool.content c on q.idContent = c.idContent "
				+ "inner join surveytool.language l on c.idLanguage = l.idLanguage "
				+ "inner join surveytool.contenttype ct on c.idContentType = ct.idContentType "
				+ "where q.idQuestion = ? and l.isoName = ?";
		public final static String s_SELECT_QUESTION_CONTENTID_BY_QUESTIONID = "SELECT idContent FROM surveytool.question where idQuestion = ?";
		public final static String s_SELECT_QUESTION_BY_POLLID = "SELECT q.*, qt.name questionTypeName, qt.templateFile, qt.formFile, c.name categoryName FROM surveytool.questionbypoll qbp "
				+ "inner join surveytool.question q on qbp.idQuestion = q.idQuestion "
				+ "inner join surveytool.questiontype qt on q.idQuestionType = qt.idQuestionType "
				+ "inner join surveytool.category c on q.idCategory = c.idCategory "
				+ "where qbp.idPoll = ?";
		
		//Questionnaire
		public final static String s_SELECT_QUESTIONNAIRE = "SELECT * FROM surveytool.questionnaire q INNER JOIN surveytool.project p ON q.idProject = p.idProject WHERE q.author = ?";
		public final static String s_SELECT_QUESTIONNAIRE_PUBLICID = "SELECT publicId FROM surveytool.questionnaire WHERE idQuestionnaire = ?";
		/*public final static String s_SELECT_QUESTIONNAIRE_TABLE_INFO = "SELECT q.idQuestionnaire, q.deadLineDate, c.text title, "
				+ "(select count(*) FROM surveytool.userquestionnaire auq where auq.idQuestionnaire = q.idQuestionnaire) allUsers, "
				+ "(select count(*) FROM surveytool.userquestionnaire fuq WHERE fuq.idQuestionnaire = q.idQuestionnaire and fuq.state = ?) usersFinished "
						+ "FROM surveytool.questionnaire q "
						+ "INNER JOIN surveytool.content c ON q.idContent = c.idContent "
						+ "INNER JOIN surveytool.language l ON c.idLanguage = l.idLanguage "
						+ "INNER JOIN surveytool.contenttype ct ON c.idContentType = ct.idContentType "
						+ "WHERE q.author = ? and l.isoName = ? and ct.name = ? ";*/
		public final static String s_SELECT_QUESTIONNAIRE_TABLE_INFO = "SELECT q.idQuestionnaire, q.deadLineDate, c.text title, "
				+ "(select count(*) FROM surveytool.userquestionnaire auq where auq.idQuestionnaire = q.idQuestionnaire) allUsers, "
				+ "(select count(*) FROM surveytool.userquestionnaire fuq WHERE fuq.idQuestionnaire = q.idQuestionnaire and fuq.state = ?) usersFinished "
						+ "FROM surveytool.questionnaire q "
						+ "INNER JOIN surveytool.content c ON q.idContent = c.idContent "
						+ "INNER JOIN surveytool.language l ON c.idLanguage = l.idLanguage "
						+ "INNER JOIN surveytool.contenttype ct ON c.idContentType = ct.idContentType "
						+ "WHERE q.author = ? and ct.name = ? and q.defaultLanguage = l.idLanguage";
		public final static String s_SELECT_QUESTIONNAIRE_TABLE_INFO_ANONIMOUS = "SELECT q.idQuestionnaire, q.deadLineDate, c.text title, "
				+ "(select count(*) FROM surveytool.anonimoususer auq where auq.idQuestionnaire = q.idQuestionnaire) allUsers "
						+ "FROM surveytool.questionnaire q "
						+ "INNER JOIN surveytool.content c ON q.idContent = c.idContent "
						+ "INNER JOIN surveytool.language l ON c.idLanguage = l.idLanguage "
						+ "INNER JOIN surveytool.contenttype ct ON c.idContentType = ct.idContentType "
						+ "WHERE q.author = ? and l.isoName = ? and ct.name = ? ";		
		public final static String s_SELECT_QUESTIONNAIRE_BY_ID = "SELECT p.projectName, ct.name contentTypeName, c.text, l.isoName, q.publicId, q.author, q.defaultLanguage FROM surveytool.questionnaire q "
				+ "inner join surveytool.project p on p.idProject = q.idProject "
				+ "inner join surveytool.content c on c.idContent = q.idContent "
				+ "inner join surveytool.contenttype ct on c.idContentType = ct.idContentType "
				+ "inner join surveytool.language l on l.idLanguage = c.idLanguage "
				+ "where q.idQuestionnaire = ?";
		public final static String s_SELECT_QUESTIONNAIRE_BY_ID_AND_LANG = "SELECT p.projectName, ct.name contentTypeName, c.text, l.isoName, q.publicId, q.author, q.defaultLanguage FROM surveytool.questionnaire q "
				+ "inner join surveytool.project p on p.idProject = q.idProject "
				+ "inner join surveytool.content c on c.idContent = q.idContent "
				+ "inner join surveytool.contenttype ct on c.idContentType = ct.idContentType "
				+ "inner join surveytool.language l on l.idLanguage = c.idLanguage "
				+ "where q.idQuestionnaire = ? and  l.isoName = ?";
		public final static String s_SELECT_QUESTIONNAIRE_BY_PUBLIC_ID = "SELECT q.*, p.projectName FROM surveytool.questionnaire q "
				+ "inner join surveytool.project p on p.idProject = q.idProject "
				+ "where publicId = ?";
		public final static String s_SELECT_QUESTIONNAIRE_CONTENTID = "SELECT idContent FROM surveytool.questionnaire WHERE idQuestionnaire = ?";
		public final static String s_SELECT_QUESTIONNAIRE_PROJECTID = "SELECT idProject FROM surveytool.questionnaire WHERE idQuestionnaire = ?";
		public final static String s_SELECT_QUESTIONNAIRESID_BY_PROJECTID = "SELECT idQuestionnaire FROM surveytool.questionnaire WHERE idProject = ?";
		
		//QuestionByPage
		public final static String s_SELECT_QUESTIONBYPAGE_BY_PAGEID_MAX_MIN = "SELECT * FROM surveytool.questionbypage where idPage = ? ## order by `index`";
		public final static String s_SELECT_QUESTIONBYPAGE_MANDATORY = "SELECT mandatory FROM surveytool.questionbypage where idQuestion = ? and idPage = ?";
		public final static String s_SELECT_QUESTIONBYPAGE_OPTIONALANSWER = "SELECT optionalAnswer FROM surveytool.questionbypage where idQuestion = ? and idPage = ?";
		public final static String s_SELECT_QUESTIONBYPAGE_INDEX = "SELECT `index` FROM surveytool.questionbypage where idQuestion = ? and idPage = ?";
		public final static String s_SELECT_NUMQUESTION_BY_PAGE = "SELECT COUNT(*) numQuestions FROM surveytool.questionbypage where idPage = ?";
		public final static String s_SELECT_QUESTIONBYPAGE_QUESTIONID_BY_PAGEID_INDEX = "SELECT idQuestion FROM surveytool.questionbypage where idPage = ? and `index` = ?";
		
		//QuestionParameter
		public final static String s_SELECT_QUESTIONPARAMETER_BY_PAGEID = "SELECT qp.parameterName, pfq.value FROM surveytool.parameterforquestion pfq "
				+ "inner join surveytool.questionparameter qp on qp.idParameter = pfq.idParameter "
				+ "where pfq.idPage = ?";
		public final static String s_SELECT_QUESTIONPARAMETERPOLL_BY_POLLID = "SELECT qp.parameterName, pfq.value FROM surveytool.parameterforquestionpoll pfq "
				+ "inner join surveytool.questionparameter qp on qp.idParameter = pfq.idParameter "
				+ "where pfq.idPoll = ?";
		public final static String s_SELECT_QUESTIONPARAMETER_BY_QUESTIONID = "SELECT qp.parameterName, pfq.value FROM surveytool.parameterforquestion pfq "
				+ "inner join surveytool.questionparameter qp on qp.idParameter = pfq.idParameter "
				+ "where pfq.idQuestion = ?";
		public final static String s_SELECT_QUESTIONPARAMETERPOLL_BY_QUESTIONID = "SELECT qp.parameterName, pfq.value FROM surveytool.parameterforquestionpoll pfq "
				+ "inner join surveytool.questionparameter qp on qp.idParameter = pfq.idParameter "
				+ "where pfq.idQuestion = ?";
		public final static String s_SELECT_QUESTIONPARAMETER_BY_PARAMETERNAME = "SELECT pfq.value FROM surveytool.parameterforquestion pfq "
				+ "inner join surveytool.questionparameter qp on qp.idParameter = pfq.idParameter "
				+ "where qp.parameterName = ?";
		public final static String s_SELECT_QUESTIONPARAMETERPOLL_BY_PARAMETERNAME = "SELECT pfq.value FROM surveytool.parameterforquestionpoll pfq "
				+ "inner join surveytool.questionparameter qp on qp.idParameter = pfq.idParameter "
				+ "where qp.parameterName = ?";
		public final static String s_SELECT_QUESTIONPARAMETER_BY_QUESTIONID_PAGEID = "SELECT qp.parameterName, pfq.value FROM surveytool.parameterforquestion pfq "
				+ "inner join surveytool.questionparameter qp on qp.idParameter = pfq.idParameter "
				+ "where pfq.idQuestion = ? and pfq.idPage = ?";
		public final static String s_SELECT_QUESTIONPARAMETER_BY_QUESTIONID_PAGEID_PARAMETERNAME = "SELECT pfq.value FROM surveytool.parameterforquestion pfq "
				+ "inner join surveytool.questionparameter qp on qp.idParameter = pfq.idParameter "
				+ "where pfq.idQuestion = ? and pfq.idPage = ? and qp.parameterName=?";
		public final static String s_SELECT_QUESTIONPARAMETERPOLL_BY_QUESTIONID_POLLID = "SELECT qp.parameterName, pfq.value FROM surveytool.parameterforquestionpoll pfq "
				+ "inner join surveytool.questionparameter qp on qp.idParameter = pfq.idParameter "
				+ "where pfq.idQuestion = ? and pfq.idPoll = ?";
		public final static String s_SELECT_QUESTIONPARAMETERPOLL_BY_QUESTIONID_POLLID_PARAMETERNAME = "SELECT pfq.value FROM surveytool.parameterforquestionpoll pfq "
				+ "inner join surveytool.questionparameter qp on qp.idParameter = pfq.idParameter "
				+ "where pfq.idQuestion = ? and pfq.idPoll = ? and qp.parameterName=?";
		public final static String s_SELECT_QUESTIONPARAMETER_ID_FOR_NAME = "SELECT idParameter FROM surveytool.questionparameter "
				+ "where parameterName = ?";

		//Register
		
		//sections
		public final static String s_SELECT_SECTIONS_BY_SURVEYID = "SELECT sc.* FROM surveytool.questionnaire s "
				+ "inner join surveytool.forma f on f.idQuestionnaire = s.idQuestionnaire "
				+ "inner join surveytool.section sc on sc.idForma = f.idForma "
				+ "where s.idQuestionnaire = ? order by sc.`index`";
		public final static String s_SELECT_SECTION_COTENTID_BY_ID = "SELECT idContent FROM surveytool.section where idSection = ?";
		public final static String s_SELECT_SECTION_NUM_ROWS_BY_SURVEYID = "SELECT count(*) " + DBFieldNames.s_NUM_ELEMENTS + " FROM surveytool.questionnaire s "
				+ "inner join surveytool.forma f on f.idQuestionnaire = s.idQuestionnaire "
				+ "inner join surveytool.section sc on sc.idForma = f.idForma "
				+ "where s.idQuestionnaire = ?";
		
		//User Questionnaire
		public final static String s_SELECT_USERS_BY_QUESTIONNAIREID = "SELECT count(*) FROM surveytool.userquestionnaire WHERE idQuestionnaire = ?";
		public final static String s_SELECT_USERS_BY_QUESTIONNAIREID_FINISHED = "SELECT count(*) FROM surveytool.userquestionnaire WHERE idQuestionnaire = ? and state = ?";
		
	//inserts
		//User
			public final static String s_INSERT_USER = "INSERT INTO `surveytool`.`user` (`userName`,`email`,`password`,`anonymous`,`idRol`,`idLanguage`) VALUES (?,?,?,?,?,?)";
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
			public final static String s_INSERT_OPTIONS_GROUP = "INSERT INTO `surveytool`.`optionsgroup` (`idQuestion`, `idContent`, `idOptionType`, `index`) VALUES (?, ?, "
					+ "(SELECT idOptionType FROM surveytool.optiontype where name = ?), ?)";
		//poll
			public final static String s_INSERT_POLL = "INSERT INTO `surveytool`.`poll` (`publicId`, `author`, `idQuestionnaire`, `idContent`, `idProject`, `callUrl`) VALUES (?, ?, ?, ?, ?, ?)";
		//page
			public final static String s_INSERT_PAGE = "INSERT INTO `surveytool`.`page` (`idSection`, `numPage`) VALUES (?, ?)";
		//project	
			public final static String s_INSERT_PROJECT = "INSERT INTO `surveytool`.`project` (`projectName`) VALUES (?)";

		//Question
			public final static String s_INSERT_QUESTION = "INSERT INTO `surveytool`.`question` (`tag`, `idQuestionType`, `idContent`, `idCategory`) VALUES (?, "
					+ "(select idQuestionType from surveytool.questiontype where name = ?), ?, "
					+ "(select idCategory from surveytool.category where name = ?))";
		//QuestionByPage
			public final static String s_INSERT_QUESTION_BY_PAGE = "INSERT INTO `surveytool`.`questionbypage` (`idPage`, `idQuestion`, `index`, `mandatory`, `optionalAnswer`) VALUES (?, ?, ?, ?, ?)";
		//ParameterForQuestion
			public final static String s_INSERT_PARAMETER_FOR_QUESTION = "INSERT INTO `surveytool`.`parameterforquestion` (`idQuestion`, `idPage`, `idParameter`, `value`) VALUES (?, ?, (SELECT idParameter FROM surveytool.questionparameter where parameterName = ?), ?)";
		//ParameterForQuestionPoll
			public final static String s_INSERT_PARAMETER_FOR_QUESTION_POLL = "INSERT INTO `surveytool`.`parameterforquestionpoll` (`idQuestion`, `idPoll`, `idParameter`, `value`) VALUES (?, ?, (SELECT idParameter FROM surveytool.questionparameter where parameterName = ?), ?)";
		//QuestionByPoll
			public final static String s_INSERT_QUESTION_BY_POLL = "INSERT INTO `surveytool`.`questionbypoll` (`idPoll`, `idQuestion`, `index`) VALUES (?, ?, ?)";
		//QuestionResource
			public final static String s_INSERT_QUESTION_RESOURCE = "INSERT INTO `surveytool`.`questionresource` (`idQuestion`, `idResoruces`) VALUES (?, ?)";
		//Questionnaire
			public final static String s_INSERT_QUESTIONNAIRE = "INSERT INTO `surveytool`.`questionnaire` (`state`, `idContent`, `idProject`, `publicId`, `author`, `defaultLanguage`) VALUES (?, ?, ?, ?, ?, ?)";
		//Resource
			public final static String s_INSERT_RESOURCE = "INSERT INTO `surveytool`.`resoruces` (`idResourceType`, `urlPath`, `idContent`) VALUES ((SELECT idResourceType FROM surveytool.resourcetype where name = ?), ?, ?)";
		//Responses
			public final static String s_INSERT_RESPONSE = "INSERT INTO `surveytool`.`responses` (`idQuestion`, `idOptionsGroup`, `value`, `idPoll`) VALUES (?, ?, ?, ?)";
		//Section
			public final static String s_INSERT_SECTION = "INSERT INTO `surveytool`.`section` (`index`, `idForma`, `idContent`) VALUES (?, ?, ?)";
			
	//updates
		//content
			public final static String s_UPDATE_CONTENT_TEXT = "UPDATE `surveytool`.`content` SET `text`= ? "
					+ "WHERE `idContent`= ? "
					+ "and`idLanguage`= (SELECT idLanguage FROM surveytool.language where isoName = ?) "
					+ "and`idContentType`= (SELECT idContentType FROM surveytool.contenttype where name = ?)";
		//optionsGroup
			public final static String s_UPDATE_OPTIONSGROUP_INDEX = "UPDATE `surveytool`.`optionsgroup` SET `index`=? WHERE `idOptionsGroup`=?";
			public final static String s_UPDATE_OPTIONSGROUP_TYPE = "UPDATE `surveytool`.`optionsgroup` SET `idOptionType`=(SELECT idOptionType FROM `surveytool`.`optionType` WHERE name=?) WHERE `idQuestion`=?";
		//optionsByCroup
			public final static String s_UPDATE_OPTIONSBYGROUP_INDEX = "UPDATE `surveytool`.`optionsbygroup` SET `index`=? WHERE `idOptionsGroup`=? and`idOption`=?";
		//project
			public final static String s_UPDATE_PROJECT_NAME = "UPDATE surveytool.project SET projectName=? WHERE idProject=?";
		//questionByPage
			public final static String s_UPDATE_QUESTIONBYPAGE_MANDATORY = "UPDATE surveytool.questionbypage SET mandatory=? WHERE idPage=? and idQuestion=?";
			public final static String s_UPDATE_QUESTIONBYPAGE_OPTIONALANSWER = "UPDATE surveytool.questionbypage SET optionalAnswer=? WHERE idPage=? and idQuestion=?";
			public final static String s_UPDATE_QUESTIONBYPAGE_INDEX = "UPDATE surveytool.questionbypage SET `index`=? WHERE idPage=? and idQuestion=?";			
		//ParameterForQuestion
			public final static String s_UPDATE_PARAMETERFORQUESTION = "UPDATE surveytool.parameterforquestion SET value=? WHERE idPage=? and idQuestion=? and idParameter=(SELECT idParameter FROM surveytool.questionparameter where parameterName = ?)";
		//ParameterForQuestionPoll
			public final static String s_UPDATE_PARAMETERFORQUESTIONPOLL = "UPDATE surveytool.parameterforquestionpoll SET `value`=? WHERE idPoll=? and idQuestion=? and idParameter=(SELECT idParameter FROM surveytool.questionparameter where parameterName = ?)";
		//questionnaire
			public final static String s_UPDATE_QUESTIONNAIRE_PROJECT = "UPDATE surveytool.questionnaire SET idProject=? WHERE idQuestionnaire= ?";
		//resources
			public final static String s_UPDATE_RESOURCE_URLPATH = "UPDATE `surveytool`.`resoruces` SET `urlPath`=? WHERE `idResoruces`=?";
		//user
			public final static String s_UPDATE_USER_PASSWORD_AND_EMAIL = "UPDATE surveytool.user SET password=?,email=?,idLanguage=? WHERE idUser= ?";
			
	//delete
		//contentIndex
			public final static String s_DELETE_CONTENT = "DELETE FROM `surveytool`.`contentindex` WHERE `idContent`=?";
			
		//content
			public final static String s_DELETE_CONTENT_BY_ID_TYPE_LANG = "DELETE FROM `surveytool`.`content` WHERE `idContent`=? "
					+ "and`idLanguage`= (SELECT idLanguage FROM surveytool.language where isoName = ?) "
					+ "and`idContentType`= (SELECT idContentType FROM surveytool.contenttype where name = ?)";
		
		//option
			public final static String s_DELETE_OPTION = "DELETE FROM surveytool.option WHERE idOption = ?";
			
		//optionsgroup
			public final static String s_DELETE_OPTIONSGROUP = "DELETE FROM surveytool.optionsgroup WHERE idOptionsGroup = ?";
			
		//page
			public final static String s_DELETE_PAGE = "DELETE FROM surveytool.page WHERE idPage = ?";
			
		//questionByPage
			public final static String s_DELETE_QUESTION_BY_PAGE = "DELETE FROM surveytool.questionbypage WHERE idPage=? and idQuestion=?";
			public final static String s_DELETE_QUESTION_BY_PAGE_WHERE_PAGEID = "DELETE FROM surveytool.questionbypage WHERE idPage=?";
			
		//ParameterForQuestion
			public final static String s_DELETE_PARAMETERFORQUESTION_BY_PAGE_QUESTION_ID = "DELETE FROM surveytool.parameterforquestion WHERE idPage=? and idQuestion=?";
			public final static String s_DELETE_PARAMETERFORQUESTION_BY_PAGE_QUESTION_ID_PARAMETER_NAME = "DELETE FROM surveytool.parameterforquestion WHERE idPage=? and idQuestion=? and idParameter=(SELECT idParameter FROM surveytool.questionparameter where parameterName = ?)";
			
		//ParameterForQuestionPoll
			public final static String s_DELETE_PARAMETERFORQUESTIONPOLL_BY_POLL_QUESTION_ID = "DELETE FROM surveytool.parameterforquestionpoll WHERE idPoll=? and idQuestion=?";
			public final static String s_DELETE_PARAMETERFORQUESTIONPOLL_BY_POLL_QUESTION_ID_PARAMETER_NAME = "DELETE FROM surveytool.parameterforquestionpoll WHERE idPoll=? and idQuestion=? and idParameter=(SELECT idParameter FROM surveytool.questionparameter where parameterName = ?)";
		
		//resources
			public final static String s_DELETE_RESOURCE = "DELETE FROM `surveytool`.`resoruces` WHERE `idResoruces`=?";

		//section
			public final static String s_DELETE_SECTION = "DELETE FROM surveytool.section WHERE idSection = ?";
		
}
