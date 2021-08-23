package view;

import controller.ProcessosController;

public class Principal {

	public static void main(String[] args) {
		ProcessosController procController = new ProcessosController();
		
		//Verificando o S.O.
		String os = procController.os();
		System.out.println(os);
		
		//Chamando um processo
		String process = "C:\\Windows\\write.exe";
		procController.callProcess(process);
		
		//Chamando um processo com o caminho errado
		process = "C:\\Windows\\writ.exe";
		procController.callProcess(process);
		
		//Chamando um processo que precisa de autorização
		process = "C:\\Windows\\regedit.exe";
		procController.callProcess(process);
		
		//Chamando um processo que precisa de autorização, mas está com o caminho errado
		process = "C:\\Windows\\regedi.exe";
		procController.callProcess(process);
		
		//Lendo e imprimindo um processo
		process = "TASKLIST /FO TABLE";
		procController.readProcess(process);
		
		//Lendo e imprimindo outro processo
		process = "PING -t4 www.google.com.br";
		procController.readProcess(process);
		
		//Lendo e imprimindo outro processo
		process = "TRACERT www.uol.com.br";
		procController.readProcess(process);
		
		//Lendo e imprimindo outro processo
		process = "ipconfig";
		procController.readProcess(process);
		
		//Matando um processo aberto pelo Nome
		String param = "cmd.exe";
		procController.killProcess(param);

		//Abrindo um processo...
		process = "C:\\Windows\\write.exe";
		procController.callProcess(process);
		//...abrindo outro processo para poder descobrir o PID do primeiro...
		process = "TASKLIST /FO TABLE";
		procController.readProcess(process);
		//...mandando matar o processo pelo seu PID
		param = "1432";
		procController.killProcess(param);		
	}
}
