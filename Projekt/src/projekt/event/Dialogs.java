package projekt.event;

/**
 *
 * @author johannes 
 */
public final class Dialogs {
	public static final int MAXMESSAGELENGTH=100;
	public static final class hello{
		public static final String sayHello="hej";
	}

	private static class delete {

		public delete() {
		}
	}
	boolean endof=true;
	String []message;
	void initDialog(String message){
		if(message.length() >MAXMESSAGELENGTH)
			// TODO fix spliting of the message
		this.message=message.split("");
		this.endof=false;
	}
	String nextMessage(){
		String m="";
		for (int i=0;message[i].equals("");i++){
			m=message[i];
			message[i]="";
			if(i==message.length){
				endof=true;
				return "";
			}
		}
		return m;
	}
}
