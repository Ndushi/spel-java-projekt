package projekt.event;

/**
 *
 * @author johannes 
 */
public final class Dialogs {
	public static final int MAXMESSAGELENGTH=100;
	public static final class Begin{
		public static final String sayHello="hej";
	}
	public static boolean endof=true;
	public static String []message;
	public static void initDialog(String mes){
		//if(Dialogs.message.length >MAXMESSAGELENGTH)
			// TODO fix spliting of the message
		if(endof){
			Dialogs.message=mes.split("\n");
			Dialogs.endof=false;
		}
	}
	public static String nextMessage(){
		String m="";
		for (int i=0;Dialogs.message.length>i;i++){
			if(!Dialogs.message[i].equals("")){
				m=Dialogs.message[i];
				Dialogs.message[i]="";
				return m;
			}
		}
		endof=true;
		return "";
	}
}
