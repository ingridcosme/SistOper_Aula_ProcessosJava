package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessosController {

	public ProcessosController() {
		super();
	}
	
	public String os() {
		//Retorna o Sistema Operacional que está em execução na máquina
		String os = System.getProperty("os.name");
		String arch = System.getProperty("os.arch");
		String version = System.getProperty("os.version");
		return os + " - v. " + version + " - arch. " + arch;
	}
	
	public void callProcess(String process) {
		//Processo chama outro processo passado como parâmetro
		try {
			//Chama processo e faz tratamento de erro, se necessário
			Runtime.getRuntime().exec(process);
		} catch(Exception e) {
			//Caso apresente algum erro, podemos mostrá-lo
			e.printStackTrace();  //apenas impressão da mensagem
			
			//Tratamento de erro 740 - Elevação
			String msgErro = e.getMessage();  //msgErro recebe a primeira linha da mensagem de erro
			System.err.println(msgErro);  //imprime só a parte em vermelho da primeira linha do erro
			if(msgErro.contains("740")) {
				//Se for o erro 740, chama a tela de administrador para poder dar continuidade
				StringBuffer buffer = new StringBuffer();
				//Criando o caminho
				buffer.append("cmd /c");  //chama a tela de admin
				buffer.append(" ");
				buffer.append(process);  //caminho do processo
				//Chamando o processo novamente, mas desta vez com a tela de admin para dar continuidade
				try {
					Runtime.getRuntime().exec(buffer.toString());
				} catch (IOException e1) {
					e1.printStackTrace();  //caso dê um novo erro, mostrê-o
				}
			} else {
				//Se não for o erro esperado, 740, mostra uma mensagem do erro
				System.err.println(msgErro);
			}
		}
	}
	
	public void readProcess(String process) {
		//Processo lê e imprime o conteúdo de outro processo passado como parâmetro
		try {
			Process p = Runtime.getRuntime().exec(process);  //Recebe dados do processo
			InputStream fluxo = p.getInputStream();  //Transformando os dados em um fluxo de bits
			//Lê o fluxo e converte para string
			InputStreamReader leitor = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leitor);
			String linha = buffer.readLine();  //Recebe a primeira linha do buffer
			while(linha != null) {  //Percorre todo o buffer e imprime cada linha
				System.out.println(linha);
				linha = buffer.readLine();
			}
			buffer.close();
			leitor.close();
			fluxo.close();
		} catch (IOException e) {
			//Caso ocorra algum erro, vamos mostrá-lo
			e.printStackTrace();
		}
	}

	public void killProcess(String param) {
		//Processo mata outro processo dado o seu Nome ou PID
		String cmdPid = "TASKKILL /PID";
		String cmdNome = "TASKKILL /IM";
		
		int pid = 0;
		StringBuffer buffer = new StringBuffer();
		
		try {
			//TASKKILL /PID XXXXXX
			pid = Integer.parseInt(param);
			buffer.append(cmdPid);
			buffer.append(" ");
			buffer.append(pid);
		} catch(NumberFormatException e) {
			//Caso dê erro porque recebeu o Nome
			//TASKKILL /IM nomedoprocesso.exe
			buffer.append(cmdNome);
			buffer.append(" ");
			buffer.append(param);
		}
		
		//Chamada de processo tendo como parâmetro o comando para matar o processo especificado
		callProcess(buffer.toString());	
	}
}
