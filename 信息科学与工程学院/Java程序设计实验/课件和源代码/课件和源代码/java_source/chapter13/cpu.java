class SynStack{
	int[] stack=new int[6];
	int index=0;
	public synchronized void push(int i){	
        if(index>5){
           try{
				System.out.println("push wait...");
				wait();
				System.out.println("push wait...end...");
				}catch(InterruptedException e){
			    }
		}
		stack[index]=i;
		System.out.println("index: "+index+"  producer: "+i);
		index++;	
		notify();
		return;
		} 
	 
	public synchronized int pop(){
        if(index<1){
           try{
				System.out.println("pop wait...");
				wait();
				System.out.println("pop wait...end...");
				}catch(InterruptedException e){
			    }
		} 

		index--;
		System.out.println("index: "+ index+"  consumer: "+stack[index]);
		System.out.println();
		notify();
		return stack[index];
		} 	
};


class Producer implements Runnable{
   SynStack synstack;
   public   Producer(SynStack synstack){
	   this.synstack=synstack;
   }
   public void run(){
	   int i=0;
	   while(true){
		   synstack.push(i);		 
		   i++;		    
		   try{
		     Thread.sleep((int)(Math.random()*1000));
	       }catch(InterruptedException e){
		   }
		    
	   }
   }
};

class Consumer implements Runnable{
   SynStack synstack;
   public Consumer(SynStack synstack){
	   this.synstack=synstack;
   }
   public void run(){
	   int i=0;
	   while(true){
		   i=synstack.pop();		  
		   try{
		     Thread.sleep((int)(Math.random()*1000));
	       }catch(InterruptedException e){
		   }		    
	   }
   }
};

public class  cpu
{
	public static void main(String[] args) 
	{
		 SynStack synstack=new SynStack();
		 Runnable producer=new Producer(synstack); 
         Runnable consumer=new Consumer(synstack);
		 Thread producerThread=new Thread(producer);
		 Thread consumerThread=new Thread(consumer);
		 producerThread.start();
		 consumerThread.start();
	}
}
