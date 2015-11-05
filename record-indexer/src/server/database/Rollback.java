package server.database;

public class Rollback {
	static Database db =new Database();
	public static void main(String[] args){
		db.endTransaction(false);
	}
}
