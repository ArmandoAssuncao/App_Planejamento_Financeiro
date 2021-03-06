package app;

import persistencia.PlanejamentoFinanceiroDAO;
import gui.framePrincipal.GuiPrincipal;
import gui.temas.Tema;

/**
 * Esta classe inicia o aplicativo de gerenciamento financeiro.
 * 
 * @author Armando Assunção
 * @author Richardson William
 *
 */
public class PlanejamentoFinanceiro {
	
	/**
	 * Cria uma instância do programa
	 * @param args <code>String[]</code> com os argumentos de linha de comando. Não são utilizados.
	 */
	public static void main(String[] args) { 
		//inicia as threads
		PlanejamentoFinanceiroDAO bancoDados = PlanejamentoFinanceiroDAO.getInstance();
		Thread threadBancoDeDados = new Thread(bancoDados);
		threadBancoDeDados.start();

		try {
			threadBancoDeDados.join();//faz o programa so iniciar apos o banco de dados iniciar
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		Tema.mudaTema();
		new GuiPrincipal();
	}
}
