package gui.temas;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * 
 *
 */
public class TemasPadrao extends Tema implements InterfaceTema {

	private List<LookAndFeelInfo> listaTemas;
	private JMenu menu;
	
	/** Cria uma instancia da classe
	 * @param grupoBotoes <code>ButtonGroup</code> com o grupo de botoes
	 * @param temaAtual <code>String</code> com o tema atual
	 */
	public TemasPadrao(ButtonGroup grupoBotoes, String temaAtual){
		this.criaListaTemas();
		this.createMenuTemas(grupoBotoes, temaAtual);
	}
	
	/* (non-Javadoc)
	 * @see com.arthurassuncao.sistel.gui.temas.InterfaceTema#createMenuTemas(javax.swing.ButtonGroup, javax.swing.LookAndFeel)
	 */
	@Override
	public JMenu createMenuTemas(ButtonGroup grupoBotoes, String temaAtual) {
		this.menu = new JMenu("Temas Padrao");
		for (LookAndFeelInfo tema : this.listaTemas){
			JMenuItem item = Tema.createRadioMenuItem(tema.getName());
			item.addActionListener(new ItemMenuThemeListener(tema.getName()));
			if (tema.getName().equals(temaAtual)){
				item.setSelected(true);
				System.out.println("Tema usado: " + tema.getName());
			}
			grupoBotoes.add(item);
			this.menu.add(item);
		}
		return this.menu;
	}

	/* (non-Javadoc)
	 * @see com.arthurassuncao.sistel.gui.temas.InterfaceTema#criaListaTemas()
	 */
	@Override
	public void criaListaTemas() {
		this.listaTemas = new ArrayList<UIManager.LookAndFeelInfo>();
		for (LookAndFeelInfo tema : UIManager.getInstalledLookAndFeels()) {
			this.listaTemas.add(tema);
		}
	}

	/* (non-Javadoc)
	 * @see com.arthurassuncao.sistel.gui.temas.InterfaceTema#getMenu()
	 */
	@Override
	public JMenu getMenu() {
		return this.menu;
	}

}