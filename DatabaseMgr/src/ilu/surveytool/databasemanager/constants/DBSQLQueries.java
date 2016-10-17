package ilu.surveytool.databasemanager.constants;

public class DBSQLQueries {
	//Selects
		//AnonymousResponse
		public final static String s_SELECT_ANONYMOUS_RESPONSE_BY_SURVEY_ID = "SELECT au.idAnonimousUser, au.createDate, r.timestamp, r.idQuestion, r.idOptionsGroup, "
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
				+ "inner join surveytool.questionbypage as qbp on qbp.idQuestion = r.idQuestion "
				+ "inner join surveytool.page as p on p.idPage = qbp.idPage "
		        + "inner join surveytool.section as s on s.idSection = p.idSection "
		        + "inner join surveytool.forma as f on f.idForma = s.idForma and f.idQuestionnaire = au.idQuestionnaire "
				+ "where idQuestionnaire = ? order by idAnonimousUser";
		public final static String s_SELECT_ANONYMOUS_USER_BY_IP_ADDRESS_SURVEYID = "SELECT * FROM surveytool.anonimoususer where ipAddres = ? and idQuestionnaire = ?";
		
		public final static String s_SELECT_ANONYMOUS_RESPONSE_WITH_OPTIONID_BY_SURVEY_ID = "SELECT au.idAnonimousUser, au.createDate, r.timestamp, r.idQuestion, r.idOptionsGroup, r.value value "
		+ "FROM surveytool.anonimoususer as au "
		+ "inner join surveytool.anonimousresponse as ar on au.idAnonimousUser = ar.idAnonimousUser "
		+ "inner join surveytool.responses as r on ar.idResponse = r.idResponse "
		+ "inner join surveytool.question as q on r.idQuestion = q.idQuestion "
		+ "inner join surveytool.questiontype as qt on q.idQuestionType = qt.idQuestionType "
        + "inner join surveytool.questionbypage as qbp on qbp.idQuestion = r.idQuestion "
        + "inner join surveytool.page as p on p.idPage = qbp.idPage "
        + "inner join surveytool.section as s on s.idSection = p.idSection "
        + "inner join surveytool.forma as f on f.idForma = s.idForma and f.idQuestionnaire = au.idQuestionnaire "
		+ "where au.idQuestionnaire = ? order by idAnonimousUser";
		
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
		public final static String s_SELECT_ANONYMOUS_RESPONSE_WHERE_ALL_WITHOUT_VALUE = "SELECT * FROM surveytool.responses as r "
				+ "inner join surveytool.anonimousresponse as ar on r.idResponse = ar.idResponse "
				+ "inner join surveytool.anonimoususer as au on au.idAnonimousUser = ar.idAnonimousUser "
				+ "where au.idAnonimousUser = ? and au.idQuestionnaire = ? and r.idQuestion = ?";
		public final static String s_WHERE_ANONYMOUS_RESPONSE_OPTIONSGROUP_NO_NULL = " and r.idOptionsGroup = ?";
		public final static String s_WHERE_ANONYMOUS_RESPONSE_OPTIONSGROUP_NULL = " and isnull(r.idOptionsGroup)";
		public final static String s_SELECT_ANONYMOUS_RESPONSE_WHERE_ALL_WITH_VALUE = "SELECT * FROM surveytool.responses as r "
				+ "inner join surveytool.anonimousresponse as ar on r.idResponse = ar.idResponse "
				+ "inner join surveytool.anonimoususer as au on au.idAnonimousUser = ar.idAnonimousUser "
				+ "where au.idAnonimousUser = ? and au.idQuestionnaire = ? and r.idQuestion = ? and r.idOptionsGroup = ? and r.value = ?";
		
		public final static String s_SELECT_ANSWERED_PAGES = "SELECT p.numPage, r.timestamp FROM surveytool.anonimousresponse ar "
				+"inner join surveytool.responses r on r.idResponse = ar.idResponse " 
				+"inner join surveytool.questionbypage q on q.idQuestion = r.idQuestion "
				+"inner join surveytool.page p on p.idPage = q.idPage where ar.idAnonimousUser = ? order by r.timestamp";
	
		public final static String s_SELECT_VISITED_PAGES = "SELECT p.numPage, ap.timestamp FROM surveytool.anonimouspages ap inner join surveytool.page p on p.idPage = ap.idPage where ap.idAnonimousUser = ? order by ap.timestamp";
		
		public final static String s_SELECT_EXPECTED_ANSWER = "select r.idResponse from surveytool.anonimousresponse ar "
				+"inner join surveytool.responses r on r.idResponse = ar.idResponse "
				+"where ar.idAnonimousUser = ? and r.idQuestion = ? and r.idOptionsGroup = ? and r.value = ?";
		
		//Content
		public final static String s_SELECT_CONTENT_BY_ID_LANGUAGE = "SELECT c.idContent, ct.name contentTypeName, l.isoName, c.text FROM surveytool.content c "
				+ "inner join surveytool.contenttype ct on c.idContentType = ct.idContentType "
				+ "inner join surveytool.language l on c.idLanguage = l.idLanguage "
				+ "where c.idContent = ? and l.isoName = ?";
		
		public final static String s_SELECT_LONG_CONTENT_BY_ID_LANGUAGE = "SELECT c.idContent, ct.name contentTypeName, c.index, l.isoName, c.text FROM surveytool.content c "
				+ "inner join surveytool.contenttype ct on c.idContentType = ct.idContentType "
				+ "inner join surveytool.language l on c.idLanguage = l.idLanguage "
				+ "where c.idContent = ? and l.isoName = ? order by ct.name, c.index";
		
		public final static String s_SELECT_CONTENT_BY_ID_LANGUAGE_CONTENTTYPE = "SELECT c.idContent, ct.name contentTypeName, l.isoName, c.text FROM surveytool.content c "
				+ "inner join surveytool.contenttype ct on c.idContentType = ct.idContentType "
				+ "inner join surveytool.language l on c.idLanguage = l.idLanguage "
				+ "where c.idContent = ? and l.isoName = ? and ct.name = ?";
		
		public final static String s_SELECT_LONG_CONTENT_BY_ID_LANGUAGE_CONTENTTYPE = "SELECT c.idContent, c.index, ct.name contentTypeName, l.isoName, c.text FROM surveytool.content c "
				+ "inner join surveytool.contenttype ct on c.idContentType = ct.idContentType "
				+ "inner join surveytool.language l on c.idLanguage = l.idLanguage "
				+ "where c.idContent = ? and l.isoName = ? and ct.name = ? order by c.index";
		
		public final static String s_SELECT_CONTENT_BY_LANGUAGE_CONTENTTYPE = "SELECT c.text FROM surveytool.content c "
				+ "INNER JOIN surveytool.language l ON c.idLanguage = l.idLanguage "
				+ "INNER JOIN surveytool.contenttype ct ON c.idContentType = ct.idContentType "
				+ "WHERE l.isoName = ? and ct.name = ?";
		
		//option
		public final static String s_SELECT_OPTION_BY_OPTIONID = "SELECT * FROM surveytool.`option` where idOption = ?";
		public final static String s_SELECT_RESOURCEID_BY_OPTIONID = "SELECT idResoruces FROM surveytool.`option` where idOption = ?";
		public final static String s_SELECT_OPTION_BY_OPTIONSGROUPID = "SELECT o.idOption, o.idContent, o.idResoruces, obg.index FROM surveytool.optionsbygroup obg "
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
				+ "where q.idQuestionnaire = ? order by p.numPage";
		
		public final static String s_SELECT_PAGE_NUM_BY_QUESTIONNAIRE_ID = "SELECT p.numPage FROM surveytool.questionnaire q "
				+ "inner join surveytool.forma f on q.idQuestionnaire = f.idQuestionnaire "
				+ "inner join surveytool.section s on s.idForma = f.idForma "
				+ "inner join surveytool.page p on s.idSection = p.idSection "
				+ "where q.idQuestionnaire = ? order by p.numPage";
		
		public final static String s_SELECT_PAGES_BY_SECTIONID = "SELECT * FROM surveytool.page where idSection = ? order by numPage";
		public final static String s_SELECT_PAGE_BY_SECTIONID_NUMPAGE = "SELECT * FROM surveytool.page where idSection = ? and numPage = ?";
		public final static String s_SELECT_PAGE_BY_NUMPAGE_SECTIONID = "SELECT * FROM surveytool.page where idSection = ? and numPage = ?";
		public final static String s_SELECT_PAGES_ID_BY_SECTIONID = "SELECT * FROM surveytool.page where idSection = ? order by numPage desc";
		public final static String s_SELECT_NEXT_PAGE_ID_BY_SECTIONID = "SELECT * FROM surveytool.page where idSection = ? and numPage = (? + 1)";
		public final static String s_SELECT_PAGE_BY_PAGEID = "SELECT * FROM surveytool.page where idPage = ?";
		public final static String s_SELECT_NUM_PAGE_BY_SECTIONID = "SELECT count(*) " + DBFieldNames.s_NUM_ELEMENTS + " FROM surveytool.page where idSection = ?";
		public final static String s_SELECT_MAX_PAGE_BY_SECTIONID = "SELECT MAX(numPage) AS numPage FROM surveytool.page where idSection = ?";
		public final static String s_SELECT_NUM_PAGE_BY_PAGEID = "SELECT numPage FROM surveytool.page where idPage = ?";
		public final static String s_SELECT_PAGES_BIGER_THAN_BY_SURVEYID = "SELECT p.* FROM surveytool.page AS p "
				+ "INNER JOIN surveytool.section AS sc ON p.idSection = sc.idSection "
				+ "INNER JOIN surveytool.forma AS f ON sc.idForma = f.idForma "
				+ "INNER JOIN surveytool.questionnaire AS q ON q.idQuestionnaire = f.idQuestionnaire "
				+ "WHERE q.idQuestionnaire = ?  and p.numPage > ? order by p.numPage";
		public final static String s_SELECT_NUM_PAGES_BY_SURVEYID = "SELECT DISTINCT COUNT(p.idPage) " + DBFieldNames.s_NUM_ELEMENTS + " FROM surveytool.page AS p "
				+ "INNER JOIN surveytool.section AS sc ON p.idSection = sc.idSection "
				+ "INNER JOIN surveytool.forma AS f ON sc.idForma = f.idForma "
				+ "INNER JOIN surveytool.questionnaire AS q ON q.idQuestionnaire = f.idQuestionnaire "
				+ "WHERE q.idQuestionnaire = ? order by p.numPage";		
		public final static String s_SELECT_NUM_QUESTIONS_BY_PAGEID = "select count(*) count from surveytool.questionbypage where idPage=?";
		
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
		public final static String s_SELECT_RESOURCE_TYPES = "SELECT idResourceType, name as resourceTypeName FROM surveytool.resourcetype";
		public final static String s_SELECT_CONTENTID_BY_RESOURCEID = "select idContent from surveytool.resoruces where idResoruces = ?";
		
		//User
		public final static String s_SELECT_LOGIN = "SELECT * FROM surveytool.user u inner join surveytool.rol r on r.idRol = u.idRol inner join surveytool.language l on l.idLanguage = u.idLanguage WHERE (userName = ? or email = ?) and password = ?";
		public final static String s_SELECT_USER_EMAIL_BY_USERID = "SELECT email FROM surveytool.user where idUser = ?";
		public final static String s_SELECT_CHECK_EXISTS_USER_BY_USERNAME = "SELECT idUser FROM surveytool.user WHERE userName LIKE ?";
		public final static String s_SELECT_CHECK_EXISTS_USER_BY_EMAIL = "SELECT idUser FROM surveytool.user WHERE email LIKE ?";
		public final static String s_SELECT_CHECK_EXISTS_USER_BY_EMAIL_PROFILE = "SELECT idUser FROM surveytool.user WHERE email LIKE ? AND userName != ?";
		public final static String s_GET_IDLANGUEGE_FROM_ISONAME = "SELECT idLanguage FROM surveytool.language WHERE isoName LIKE ?";
		public final static String s_GET_ISOLANGUEGE_FROM_IDLANGUAGE = "SELECT isoName FROM surveytool.language WHERE idLanguage = ?";
		public final static String s_SELECT_LIST_LANGUAGES = "SELECT name,isoName FROM surveytool.language";
				
		//Register
		
		//Question
		public final static String s_SELECT_QUESTION_BY_SURVEYID = "SELECT q.*, qp.index, qt.name questionTypeName, qt.templateFile, qt.formFile, qt.statisticResultsFile, c.name categoryName, qp.mandatory, qp.optionalAnswer, qp.idPage "
				+ "FROM surveytool.questionnaire s "
				+ "inner join surveytool.forma f on f.idQuestionnaire = s.idQuestionnaire "
				+ "inner join surveytool.section sc on sc.idForma = f.idForma "
				+ "inner join surveytool.page p on sc.idSection = p.idSection "
				+ "inner join surveytool.questionbypage qp on qp.idPage = p.idPage "
				+ "inner join surveytool.question q on q.idQuestion = qp.idQuestion "
				+ "inner join surveytool.questiontype qt on q.idQuestionType = qt.idQuestionType "
				+ "inner join surveytool.category c on q.idCategory = c.idCategory "
				+ "where s.idQuestionnaire = ? order by qp.index";
		public final static String s_SELECT_QUESTION_BY_SECTIONID = "SELECT q.*, qp.`index`, qt.name questionTypeName, qt.templateFile, qt.formFile, qt.statisticResultsFile, c.name categoryName, qp.mandatory, qp.optionalAnswer, qp.idPage "
				+ "FROM surveytool.section sc "
				+ "inner join surveytool.page p on sc.idSection = p.idSection "
				+ "inner join surveytool.questionbypage qp on qp.idPage = p.idPage "
				+ "inner join surveytool.question q on q.idQuestion = qp.idQuestion "
				+ "inner join surveytool.questiontype qt on q.idQuestionType = qt.idQuestionType "
				+ "inner join surveytool.category c on q.idCategory = c.idCategory "
				+ "where sc.idSection = ? order by qp.`index`";
		public final static String s_SELECT_QUESTION_BY_PAGEID = "SELECT q.*, qp.`index`, qt.name questionTypeName, qt.templateFile, qt.formFile, qt.statisticResultsFile, c.name categoryName, qp.mandatory, qp.optionalAnswer, qp.idPage "
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
		
		public final static String s_SELECT_QUESTION_CONTENTS_QUESTIONID_LANGUAGE = "SELECT cQ.text question, oG.idOptionsGroup, if(q.idQuestionType=4, (select cOG.text from content cOG where cOG.idContent = oG.idContent and cOG.idContentType=1 and cOG.idLanguage=cQ.idLanguage),\"\") as optionsGroup, o.idOption, cO.text options FROM question q "
				+ "inner join content cQ on cQ.idContent = q.idContent "
				+ "inner join optionsgroup oG on oG.idQuestion = q.idQuestion "
				+ "inner join optionsbygroup obg on obg.idOptionsGroup = oG.idOptionsGroup "
				+ "inner join surveytool.option o on o.idOption = obg.idOption "
				+ "inner join content cO on cO.idContent = o.idContent "
				+ "inner join language l on l.idLanguage = cQ.idLanguage "
				+ "where q.idQuestion = ? and l.isoName = ? and cQ.idContentType=1 and cO.idContentType=1 and cO.idLanguage=cQ.idLanguage order by og.idOptionsGroup, o.idOption";
		
		public final static String s_SELECT_SURVEY_QUESTION_CONTENTS_SURVEYID_LANGUAGE = "SELECT q.idQuestion, q.idQuestionType, oG.idOptionsGroup, if(q.idQuestionType=4, (select cOG.text from content cOG where cOG.idContent = oG.idContent and cOG.idContentType=1 and cOG.idLanguage=cO.idLanguage),\"\") as optionsGroup, o.idOption, cO.text options FROM " 
				+ "(select ques.* from question ques "
				+ "inner join surveytool.questionbypage AS qbp  on qbp.idQuestion = ques.idQuestion "
				+ "inner join surveytool.page AS p  on p.idPage = qbp.idPage "
				+ "INNER JOIN surveytool.section AS sc ON p.idSection = sc.idSection "
				+ "INNER JOIN surveytool.forma AS f ON sc.idForma = f.idForma "
				+ "INNER JOIN surveytool.questionnaire AS q ON q.idQuestionnaire = f.idQuestionnaire "
				+ "WHERE q.idQuestionnaire = ?) as q "
				+ "inner join optionsgroup oG on oG.idQuestion = q.idQuestion "
				+ "inner join optionsbygroup obg on obg.idOptionsGroup = oG.idOptionsGroup "
				+ "inner join surveytool.option o on o.idOption = obg.idOption "
				+ "inner join content cO on cO.idContent = o.idContent "
				+ "inner join language l on l.idLanguage = cO.idLanguage "
				+ "where l.isoName = ? and cO.idContentType=1 order by og.idOptionsGroup, o.idOption";
		public final static String s_SELECT_QUESTIONS_TYPES_BY_SURVEY_PUBLICID_PAGEID = "SELECT qt.name questionTypeName FROM surveytool.questionnaire s "
				+ "inner join surveytool.forma f on f.idQuestionnaire = s.idQuestionnaire "
				+ "inner join surveytool.section sc on sc.idForma = f.idForma "
				+ "inner join surveytool.page p on sc.idSection = p.idSection "
				+ "inner join surveytool.questionbypage qp on qp.idPage = p.idPage "
				+ "inner join surveytool.question q on q.idQuestion = qp.idQuestion "
				+ "inner join surveytool.questiontype qt on q.idQuestionType = qt.idQuestionType "
				+ "where s.idQuestionnaire = ? and p.numPage = ?";		
		
		
		public final static String s_SELECT_QUESTION_TYPE_QUESTIONID = "SELECT q.idQuestionType FROM question q "
				+ "where q.idQuestion = ?";
		
		public final static String s_SELECT_QUESTION_OPTIONS_QUESTIONID_LANGUAGE = "SELECT cQ.text question, r.value FROM question q "
				+ "inner join content cQ on cQ.idContent = q.idContent "
				+ "inner join responses r on r.idQuestion = q.idQuestion "
				+ "inner join language l on l.idLanguage = cQ.idLanguage "
				+ "where q.idQuestion = ? and cQ.idContentType=1 and l.isoName = ? order by r.idResponse";

		
		
		
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
						//+ "INNER JOIN surveytool.language l ON c.idLanguage = l.idLanguage "
						+ "INNER JOIN surveytool.contenttype ct ON c.idContentType = ct.idContentType "
						+ "WHERE q.author = ? and c.idLanguage = q.defaultLanguage and ct.name = ? ";
		public final static String s_SELECT_QUESTIONNAIRE_BY_ID = "SELECT p.projectName, ct.name contentTypeName, c.text, l.isoName, q.publicId, q.author, q.defaultLanguage, q.objetive FROM surveytool.questionnaire q "
				+ "inner join surveytool.project p on p.idProject = q.idProject "
				+ "inner join surveytool.content c on c.idContent = q.idContent "
				+ "inner join surveytool.contenttype ct on c.idContentType = ct.idContentType "
				+ "inner join surveytool.language l on l.idLanguage = c.idLanguage "
				+ "where q.idQuestionnaire = ?";
		public final static String s_SELECT_QUESTIONNAIRE_BY_ID_AND_LANG = "SELECT p.projectName, ct.name contentTypeName, c.text, l.isoName, q.publicId, q.author, q.defaultLanguage, q.objetive FROM surveytool.questionnaire q "
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
		public final static String s_SELECT_QUESTIONNAIRE_ID_BY_PUBLICID = "SELECT idQuestionnaire FROM surveytool.questionnaire WHERE publicId = ?";
		public final static String s_SELECT_QUESTIONNAIRESID_BY_PROJECTID = "SELECT idQuestionnaire FROM surveytool.questionnaire WHERE idProject = ?";
		public final static String s_SELECT_NUMQUESTIONS_QUESTIONNAIRE_SURVEYID = "SELECT count(*) count, q.mandatory from forma f "
				+"inner join section s on s.idForma = f.idForma "
				+"inner join page p on p.idSection = s.idSection "
				+"inner join questionbypage q on q.idPage = p.idPage "
				+"where f.idQuestionnaire = ? group by q.mandatory";
		
		//QuestionByPage
		public final static String s_SELECT_QUESTIONBYPAGE_BY_PAGEID_MAX_MIN = "SELECT * FROM surveytool.questionbypage where idPage = ? ## order by `index`";
		public final static String s_SELECT_QUESTIONBYPAGE_MANDATORY = "SELECT mandatory FROM surveytool.questionbypage where idQuestion = ? and idPage = ?";
		public final static String s_SELECT_QUESTIONBYPAGE_OPTIONALANSWER = "SELECT optionalAnswer FROM surveytool.questionbypage where idQuestion = ? and idPage = ?";
		public final static String s_SELECT_QUESTIONBYPAGE_INDEX = "SELECT `index` FROM surveytool.questionbypage where idQuestion = ? and idPage = ?";
		public final static String s_SELECT_NUMQUESTION_BY_PAGE = "SELECT COUNT(*) numQuestions FROM surveytool.questionbypage where idPage = ?";
		public final static String s_SELECT_QUESTIONBYPAGE_QUESTIONID_BY_PAGEID_INDEX = "SELECT idQuestion FROM surveytool.questionbypage where idPage = ? and `index` = ?";
		
		//Quotas
		public final static String s_SELECT_CHECK_EXISTS_QUOTAS = "SELECT * FROM surveytool.quotas WHERE idQuestionnaire=? AND idQuestion=? AND idOptionsGroup=? AND value=?";
		public final static String s_SELECT_QUOTAS_BY_IDQUESTIONNARIE = "SELECT idQuestion,idOptionsGroup, value, maxResponses,minResponses FROM surveytool.quotas WHERE idQuestionnaire=? ORDER BY idQuestion,value";
		public final static String s_SELECT_QUOTA_MAX = "SELECT maxResponses FROM surveytool.quotas WHERE value=?";
		public final static String s_SELECT_QUOTA_MIN = "SELECT minResponses FROM surveytool.quotas WHERE value=?";
		public final static String s_SELECT_OPTION_NAME_IDOPTION = "SELECT text FROM surveytool.option o INNER JOIN content c ON o.idContent = c.idContent INNER JOIN language l ON c.idLanguage = l.idLanguage WHERE l.isoname=? AND idOption=? ORDER BY o.idOption";
		public final static String s_SELECT_QUESTION_NAME_IDOPTION = "SELECT text FROM surveytool.question q INNER JOIN content c ON q.idContent = c.idContent INNER JOIN language l ON c.idLanguage = l.idLanguage WHERE l.isoname=? AND q.idQuestion=? ORDER BY q.idQuestion";
		public final static String s_SELECT_NUMBER_RESPONSES_QUOTA = "SELECT count(*) FROM responses r INNER JOIN anonimousresponse ar ON r.idResponse=ar.idResponse INNER JOIN anonimoususer au ON ar.idAnonimousUser=au.idAnonimousUser WHERE idQuestion=? AND idOptionsGroup=? AND value=? AND au.finished=1";
		public final static String s_SELECT_NUMBER_RESPONSES_SURVEY = "SELECT COUNT(*) FROM surveytool.anonimoususer WHERE idQuestionnaire=? AND finished=1 GROUP BY idQuestionnaire";
		
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
		public final static String s_SELECT_QUESTIONNAIREID_BY_QUESTIONID = "SELECT forma.idQuestionnaire from surveytool.question question "
				+ "inner join surveytool.questionbypage qbp on qbp.idQuestion = question.idQuestion "
				+ "inner join surveytool.page page on page.idPage = qbp.idPage "
				+ "inner join surveytool.section section on section.idSection = page.idSection "
				+ "inner join surveytool.forma forma on forma.idForma = section.idForma "
				+ "where question.idQuestion = ?";

		//QDependences
		public final static String s_SELECT_QDEPENDENCES_BY_QUESTIONID_LANG = "SELECT qd.idQDependences, dt.name depType, qbp.idPage, dv.idDependenceItem, dv.idQuestion, contentQuestion.text qText, dv.idOptionsGroup, dv.optionValue, contentOption.text oText FROM surveytool.qdependences qd "
				+"inner join surveytool.dependencetype dt on qd.idDependenceType = dt.idDependenceType "
				+"inner join surveytool.qdependencesvalue dv on dv.idQDependences = qd.idQDependences "
				+"inner join surveytool.option opt on opt.idOption = dv.optionValue "
				+"inner join surveytool.question question on question.idQuestion=dv.idQuestion "
				+"inner join surveytool.questionbypage qbp on qbp.idQuestion = question.idQuestion "
				+"inner join surveytool.content contentQuestion on question.idContent=contentQuestion.idContent "
				+"inner join surveytool.content contentOption on opt.idContent=contentoption.idContent "
				+"where qd.idQuestion = ? and contentQuestion.idContentType = 1 and contentOption.idContentType = 1  and contentQuestion.idLanguage = (select lang.idlanguage from surveytool.language lang where lang.isoName=?)  and contentOption.idLanguage = (select lang.idlanguage from surveytool.language lang where lang.isoName=?)";
			
		public final static String s_SELECT_QDEPENDENCES_BY_QUESTIONID_NOTEXT = "select qd.idQDependences, dt.name depType, qdv.idQuestion, qdv.idOptionsGroup, qdv.optionValue from surveytool.qdependences qd "
				+"inner join surveytool.qdependencesvalue qdv on qdv.idQDependences = qd.idQDependences "
				+"inner join surveytool.dependencetype dt on qd.idDependenceType = dt.idDependenceType "
				+"where qd.idQuestion = ?";
		
		public final static String s_SELECT_QDEPENDENCEID_BY_QUESTIONID_LANG = "SELECT qd.idQDependences FROM surveytool.qdependences qd where qd.idQuestion = ?";
		
		public final static String s_SELECT_COUNT_QDEPENDENCESVALUE = "SELECT count(*) count FROM surveytool.qdependencesvalue "
				+"where idQDependences = ?";
		
		public final static String s_SELECT_IDQDEPENDENCE_FROM_QDEPENDENCESVALUE = "SELECT idQDependences FROM surveytool.qdependencesvalue "
				+"where idDependenceItem = ?";
		
		//LogicGoTo
		public final static String s_SELECT_LOGICGOTTO_BY_IDQUESTION = "SELECT goto.idOptionsGroup, goto.optionValue, contentOption.text oText, goto.idQuestionDest, contentQuestion.text qText FROM surveytool.questionlogicgoto goto "
				+"inner join surveytool.option opt on opt.idOption = CAST(goto.optionValue AS UNSIGNED) "
				+"inner join surveytool.question question on question.idQuestion=goto.idQuestionDest "
				+"inner join surveytool.content contentQuestion on question.idContent=contentQuestion.idContent "
				+"inner join surveytool.content contentOption on opt.idContent=contentoption.idContent "
				+"where goto.idQuestion = ? and contentQuestion.idContentType = 1 and contentOption.idContentType = 1  and contentQuestion.idLanguage = (select lang.idlanguage from surveytool.language lang where lang.isoName=?)  and contentOption.idLanguage = (select lang.idlanguage from surveytool.language lang where lang.isoName=?)";
		public final static String s_SELECT_EXISTLOGICGOTTO_BY_IDQUESTION_OGID_OID = "SELECT idQuestionDest FROM surveytool.questionlogicgoto where idQuestion = ? AND idOptionsGroup = ? AND optionValue = ?";
		public final static String s_SELECT_LOGICGOTTO_BY_IDQUESTION_OGID_OID = "SELECT p.numPage FROM surveytool.questionlogicgoto goTo inner join surveytool.questionbypage qbp on qbp.idQuestion=goTo.idQuestionDest inner join surveytool.page p on p.idPage=qbp.idPage where goTo.idQuestion = ? AND goTo.idOptionsGroup = ? AND goTo.optionValue = ?";
		
		//Register
		
		//sections
		public final static String s_SELECT_SECTIONS_BY_SURVEYID = "SELECT sc.* FROM surveytool.questionnaire s "
				+ "inner join surveytool.forma f on f.idQuestionnaire = s.idQuestionnaire "
				+ "inner join surveytool.section sc on sc.idForma = f.idForma "
				+ "where s.idQuestionnaire = ? order by sc.`index`";
		public final static String s_SELECT_SECTION_BY_SURVEYID_INDEX = "SELECT sc.* FROM surveytool.questionnaire s "
				+ "inner join surveytool.forma f on f.idQuestionnaire = s.idQuestionnaire "
				+ "inner join surveytool.section sc on sc.idForma = f.idForma "
				+ "where s.idQuestionnaire = ? and  sc.index = ?";
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
			public final static String s_INSERT_ANONIMOUS_USER_WITH_IP_NUMPAGE = "INSERT INTO `surveytool`.`anonimoususer` (`idQuestionnaire`, `ipAddres`, `currentPage`) VALUES (?, ?, ?)";
		//AnonimousResponse
			public final static String s_INSERT_ANONIMOUS_RESPONSE = "INSERT INTO `surveytool`.`anonimousresponse` (`idAnonimousUser`, `idResponse`) VALUES (?, ?)";
		//content
			public final static String s_INSERT_CONTENT = "INSERT INTO `surveytool`.`content` (`idContent`, `idLanguage`, `idContentType`, `index`, `text`) VALUES (?, "
					+ "(SELECT idLanguage FROM surveytool.language WHERE isoName = ?), "
					+ "(SELECT idContentType FROM surveytool.contenttype WHERE name = ?), ?, ?)";
		//contentIndex
			public final static String s_INSERT_CONTENT_INDEX = "INSERT INTO `surveytool`.`contentindex` (`idContent`) VALUES (null);";
		//forma
			public final static String s_INSERT_FORMA = "INSERT INTO `surveytool`.`forma` (`idQuestionnaire`) VALUES (?)";
		//Option
			public final static String s_INSERT_OPTION = "INSERT INTO `surveytool`.`option` (`idContent`) VALUES (?)";
			public final static String s_INSERT_OPTION_WITHOUTCONTENT = "INSERT INTO `surveytool`.`option` (`idContent`) VALUES (?)";
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
			public final static String s_INSERT_RESPONSE_PAGES_ANONIMOUS = "INSERT INTO `surveytool`.`anonimouspages` (`idPage`, `idAnonimousUser`) VALUES (?, ?)";
			
		//QDependences
			public final static String s_INSERT_QDEPENDENCE = "INSERT INTO surveytool.qdependences (idQuestionnaire, idQuestion, idDependenceType) VALUES ((SELECT idQuestionnaire FROM surveytool.forma WHERE idForma = (SELECT idForma FROM surveytool.section where idSection= (SELECT idSection FROM surveytool.page where idPage= (SELECT idPage FROM surveytool.questionbypage where idquestion=?)))), ?, (SELECT idDependenceType FROM surveytool.dependencetype where name = ?))";
			public final static String s_INSERT_QDEPENDENCEVALUE = "INSERT INTO `surveytool`.`qdependencesvalue` (`idQDependences`, `idQuestion`, `idOptionsGroup`, `optionValue`) VALUES (?, ?, ?, ?)";
			
		//Section
			public final static String s_INSERT_SECTION = "INSERT INTO `surveytool`.`section` (`index`, `idForma`, `idContent`) VALUES (?, ?, ?)";
		
		//LogicGoTo
			public final static String s_INSERT_LOGICGOTTO = "insert into surveytool.questionlogicgoto(idQuestion, idOptionsGroup, optionValue, idQuestionDest, idQuestionnaire) VALUES (?, ?, ?, ?,(SELECT forma.idQuestionnaire from surveytool.question question "
				+ "inner join surveytool.questionbypage qbp on qbp.idQuestion = question.idQuestion "
				+ "inner join surveytool.page page on page.idPage = qbp.idPage "
				+ "inner join surveytool.section section on section.idSection = page.idSection "
				+ "inner join surveytool.forma forma on forma.idForma = section.idForma "
				+ "where question.idQuestion = ?))";

		//Quota	
			public final static String s_INSERT_QUOTA = "INSERT INTO `surveytool`.`quotas` (`idQuestionnaire`, `idQuestion`, `idOptionsGroup`,`value`,`maxResponses`,`minResponses`) VALUES (?, ?, ?, ?, ?, ?)";

	//updates
		//anonymous user
			public final static String s_UPDATE_ANONIMOUSUSER_CURRET_PAGE = "UPDATE `surveytool`.`anonimoususer` SET `currentPage`=? WHERE `idAnonimousUser`=?";
			public final static String s_UPDATE_ANONIMOUSUSER_FINISHED = "UPDATE `surveytool`.`anonimoususer` SET `finished`=? WHERE `idAnonimousUser`=?";
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

		//page
			public final static String s_UPDATE_PAGE_NUM_PAGE = "UPDATE `surveytool`.`page` SET `numPage`=? WHERE `idPage`=?";

		//option
			public final static String s_UPDATE_OPTION_IDRESOURCE = "UPDATE `surveytool`.`option` SET `idResoruces`=? WHERE `idOption`=?";

		//project
			public final static String s_UPDATE_PROJECT_NAME = "UPDATE surveytool.project SET projectName=? WHERE idProject=?";
		//questionByPage
			public final static String s_UPDATE_QUESTIONBYPAGE_MANDATORY = "UPDATE surveytool.questionbypage SET mandatory=? WHERE idPage=? and idQuestion=?";
			public final static String s_UPDATE_QUESTIONBYPAGE_OPTIONALANSWER = "UPDATE surveytool.questionbypage SET optionalAnswer=? WHERE idPage=? and idQuestion=?";
			public final static String s_UPDATE_QUESTIONBYPAGE_INDEX = "UPDATE surveytool.questionbypage SET `index`=? WHERE idPage=? and idQuestion=?";
			public final static String s_UPDATE_QUESTIONBYPAGE_INDEX_PAGEID = "UPDATE surveytool.questionbypage SET `index`=?, `idPage`=? WHERE idPage=? and idQuestion=?";
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

		//QDependences
			public final static String s_UPDATE_QDEPENDENCE_SHOW = "UPDATE `surveytool`.`qdependences` SET show=? WHERE idQDependences = ?";
			public final static String s_UPDATE_QDEPENDENCE_DEPENDENCETYPE = "UPDATE `surveytool`.`qdependences` SET idDependenceType=(SELECT idDependenceType FROM surveytool.dependencetype where name = ?) WHERE idQDependences = ?";
			public final static String s_UPDATE_QDEPENDENCEVALUE_QUESTION = "UPDATE `surveytool`.`qdependencesvalue` SET idQuestion=?, `idOptionsGroup`=?, `optionValue`=? WHERE idDependenceItem = ?";
		//LogicGoTo
			public final static String s_UPDATE_LOGICGOTTO = "UPDATE surveytool.questionlogicgoto SET idQuestionDest = ? WHERE idQuestion = ? AND idOptionsGroup = ? AND optionValue = ? AND idQuestionnaire = (SELECT forma.idQuestionnaire from surveytool.question question "
				+ "inner join surveytool.questionbypage qbp on qbp.idQuestion = question.idQuestion "
				+ "inner join surveytool.page page on page.idPage = qbp.idPage "
				+ "inner join surveytool.section section on section.idSection = page.idSection "
				+ "inner join surveytool.forma forma on forma.idForma = section.idForma "
				+ "where question.idQuestion = ?)";

		//quotas
			public final static String s_UPDATE_QUOTAS = "UPDATE surveytool.quotas SET maxResponses=?,minResponses=? WHERE idQuestionnaire= ? AND idQuestion=? AND idOptionsGroup=? AND value=?";
			public final static String s_UPDATE_OBJETIVE_SURVEY = "UPDATE surveytool.questionnaire SET objetive=? WHERE idQuestionnaire=?";

			
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
			
		//responses
			public final static String s_DELETE_RESPONSES = "DELETE r.* FROM surveytool.responses as r "
					+ "inner join surveytool.anonimousresponse as ar on ar.idResponse = r.idResponse "
					+ "inner join surveytool.anonimoususer as au on ar.idAnonimousUser = au.idAnonimousUser "
					+ "WHERE au.idAnonimousUser = ? and au.idQuestionnaire = ? and r.idQuestion = ?";

		//section
			public final static String s_DELETE_SECTION = "DELETE FROM surveytool.section WHERE idSection = ?";

		//QDependences
			public final static String s_DELETE_QDEPENDENCE = "DELETE FROM `surveytool`.`qdependences` WHERE idQDependences = ?";
			public final static String s_DELETE_QDEPENDENCEVALUE = "DELETE FROM `surveytool`.`qdependencesvalue` WHERE idDependenceItem = ?";
			public final static String s_DELETE_ALLQDEPENDENCEVALUE = "DELETE FROM `surveytool`.`qdependencesvalue` WHERE idQDependences = ?";

		//LogicGoTo
			public final static String s_DELETE_LOGICGOTTO = "DELETE FROM surveytool.questionlogicgoto WHERE idQuestion = ? AND idOptionsGroup = ? AND optionValue = ? AND idQuestionnaire = (SELECT forma.idQuestionnaire from surveytool.question question "
				+ "inner join surveytool.questionbypage qbp on qbp.idQuestion = question.idQuestion "
				+ "inner join surveytool.page page on page.idPage = qbp.idPage "
				+ "inner join surveytool.section section on section.idSection = page.idSection "
				+ "inner join surveytool.forma forma on forma.idForma = section.idForma "
				+ "where question.idQuestion = ?)";
			
			public final static String s_DELETE_ALLLOGICGOTTO = "DELETE FROM surveytool.questionlogicgoto WHERE idQuestion = ? AND idQuestionnaire = (SELECT forma.idQuestionnaire from surveytool.question question "
					+ "inner join surveytool.questionbypage qbp on qbp.idQuestion = question.idQuestion "
					+ "inner join surveytool.page page on page.idPage = qbp.idPage "
					+ "inner join surveytool.section section on section.idSection = page.idSection "
					+ "inner join surveytool.forma forma on forma.idForma = section.idForma "
					+ "where question.idQuestion = ?)";
			
		//quota
			public final static String s_DELETE_QUOTA = "DELETE FROM surveytool.quotas WHERE idQuestionnaire = ? AND idQuestion=?";
			public final static String s_DELETE_QUOTA_OPTION = "DELETE FROM surveytool.quotas WHERE idQuestionnaire= ? AND idQuestion=? AND idOptionsGroup=? AND value=?";
		

}
