/*class thread_exam_sub extends Thread{
	public void run(){
		while(true){
			try{
				System.out.println("OK");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}

class chat extends Thread{
	public void run(){
		while(true){
			try{
				System.out.println("OK_2");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}*/

class thread_exam {
	public static void main(String[] args){
		MainFrame frame = MainFrame.getInstance();
		frame.init();
	}
}
