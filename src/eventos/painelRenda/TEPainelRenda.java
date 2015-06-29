package eventos.painelRenda;

import gui.painelRenda.IgPainelRenda;
import gui.renda.JanelaCriarRenda;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TEPainelRenda implements ActionListener {
	private IgPainelRenda painelRenda;
	Window janelaPai;
	
	public TEPainelRenda(IgPainelRenda painelRenda, Window janelaPai) {
		this.painelRenda = painelRenda;
		this.janelaPai = janelaPai;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == painelRenda.getBotaoAddRenda())
			new JanelaCriarRenda(painelRenda);
		else if(e.getSource() == painelRenda.getBotaoExcluirRenda())
			System.out.println("excluir");
		else System.out.println("editar ");
		
	}

}
