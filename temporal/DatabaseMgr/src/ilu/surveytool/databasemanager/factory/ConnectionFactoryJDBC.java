package ilu.surveytool.databasemanager.factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionFactoryJDBC {
	
	//Connection
    Context ctx;
    static DataSource dataSource;
        
    /**
     * Generate connection using connection pool
     */
    public ConnectionFactoryJDBC()
    {
        // recuperamos el contexto inicial y la referencia a la fuente de datos           
         if(this.dataSource == null)
 		{
        	 this._initializeDataSource();
 		}      
            
    }
    
    public Connection getConnection()
	{
		Connection con = null;
		try {
			con = this.dataSource.getConnection();
			if(con.isClosed())
			{
				this._initializeDataSource();
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;		
	}
    
    private void _initializeDataSource()
    {
    	try {
			ctx = new InitialContext();
	        dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/surveytool");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
