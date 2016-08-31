package ilu.surveytool.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;

public class test {
	
	public test()
	{
		super();
	}
	
	private Connection _openConnection()
	{
		ConnectionFactoryJDBC connectionFactory  = new ConnectionFactoryJDBC();
		return connectionFactory.getConnection();
	}
	
	private void _closeConnections(Connection con, PreparedStatement pstm, ResultSet rs)
	{
		 try {
			 if(pstm != null)
				 pstm.close();
			 if(rs != null)
				 rs.close();
			 if (con != null)
				 con.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	}
	
	/* EJEMPLO
	 * Método que recibirá un objeto de tipo User que equivale a la tabla User
	 * de la base de datos. Este método simplemente insertará los datos en la
	 * base de datos. Como resultado este método nos devolverá identificador del
	 * usuario (userId)
	 */
	/*public int insertUser(User user) throws DatabasemanagerException {
		//System.out.println("inserUser");
		int userId = 0;
		
		Connection con = this._openConnection();
		UserCypher.encrypt(user);
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(SQLQueries.s_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
		   pstm.setString(1, user.getUserName());
		   pstm.setString(2, user.getPassword());
		   pstm.setBoolean(3, user.isActive());
		   pstm.setBoolean(4, user.isLocked());
		   pstm.setString(5, user.getEmail());
		   pstm.setInt(6, user.getBirthyear());
		   pstm.setInt(7, user.getRegistrationStep());
		   pstm.setString(8, user.getBirthdate());
		   pstm.setInt(9, user.getUserstate());
		   pstm.setInt(10, user.getPercentageTest());
		   pstm.setString(11, user.getName());
	   		pstm.setString(12, user.getSurname());
	   		pstm.setString(13, user.getSurnameTwo());
	   		pstm.setString(14, user.getNif());
	   		pstm.setString(15, user.getCity());
	   		pstm.setBoolean(16, user.isUserCaixa()); 
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   ResultSet rs = pstm.getGeneratedKeys();
			   if(rs.next())
				   userId = rs.getInt(1);
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return userId;
	}*/
	
	/*
	 * Método que recibirá como entrada el identificador del usuario y del
	 * estado al que pasa a estar el usuario después de la tarea realizada.
	 * 
	 * Simplemente actualizamos en la tabla User el campo userStateId con el
	 * valor pasado como parámetro.
	 */
	/*public void updateState(int userId, int stateId) throws DatabasemanagerException {
		//System.out.println("updateState");
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(SQLQueries.s_UPDATE_USERSTATE);
			pstm.setInt(1, stateId);
			pstm.setInt(2, userId);
		   		
			int numUpdated = pstm.executeUpdate();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}
		   
	}*/
	
	/*
	 * This method gets the user state using the name of the state.
	 *  
	 */
	/*@SuppressWarnings("unchecked")
	public Userstate userStateByName(String userStateName) throws DatabasemanagerException{
		//System.out.println("userStateByName");
		Userstate userState = null;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(SQLQueries.s_SELECT_USERSTATE_BY_NAME);			
	   		pstm.setString(1, userStateName);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			userState = new Userstate();
	   			userState.setUserStateId(rs.getInt(FieldNames.s_USER_STATE_ID));
	   			userState.setName(userStateName);
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}

		return userState;
	}*/
	

	/*
	 * Método que recibe como parámetro un nombre de usuario, y debe comprobar
	 * si ya existe en la base de datos, devolviendo el valor booleano que
	 * corresponda.
	 */
	/*@SuppressWarnings("unchecked")
	public boolean existUserName(String userName) throws DatabasemanagerException {
		//System.out.println("existUserName");
		boolean exist = false;

		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		  
		try{
			SecurityModule sm = new SecurityModule();
			String encryptUserName = sm.encrypt(userName);
			 
		   	pstm = con.prepareStatement(SQLQueries.s_SELECT_USERID_BY_USERNAME);			
	   		pstm.setString(1, encryptUserName);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			exist = true;
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityModuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}

		return exist;
	}*/
	
	/**
	 * This method removes options values related with an optionId.
	 * @param userId
	 * @throws DatabasemanagerException
	 */
	/*@SuppressWarnings("unchecked")
	public void removeUserOptionValues(Integer userId, Set<Integer> optionsId) throws DatabasemanagerException {
		//System.out.println("removeUserOptionValues");
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(SQLQueries.s_DELETE_USER_OPTION_VALUE_BY_USERID_OPTIONID);
		   	for(Integer optionId : optionsId)
		   	{
		   		pstm.setInt(1, userId);
		   		pstm.setInt(2, optionId);
	   		
		   		pstm.execute();
		   	}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

	}*/
	

}
