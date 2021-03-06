package persistencia;

import funcoes.Converte;
import gui.JanelaMensagem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import classes.Categoria;
import classes.MetaMensal;

/**
 * Classe para manipular objetos da classe <code>MetaMensal</code> no banco de dados.
 *
 * @author Armando Assunção
 * @author Richardson William
 * 
 * @see PlanejamentoFinanceiroDAO
 */
public class MetaMensalDAO extends PlanejamentoFinanceiroDAO {

	/** Insere um objeto <code>MetaMensal</code> no banco de dados
	 * @param metaMensal <code>MetaMensal</code> que será inserida no banco de dados
	 * @param descricaoCategoria <code>String</code> que será associada com a meta mensal no banco de dados
	 * @return <code>int</code> com o resultado da inserção. Este valor é uma constante definida na classe <code>BancoDeDados</code>
	 * 
	 * @see Categoria
	 * @see BancoDeDados#RESULTADO_SUCESSO
	 * @see BancoDeDados#RESULTADO_ERRO_REGISTRO_DUPLICADO
	 * @see BancoDeDados#RESULTADO_ERRO_BANCO_DADOS
	 * @see BancoDeDados#RESULTADO_ERRO_DESCONHECIDO
	 */
	public int inserir(MetaMensal metaMensal, String descricaoCategoria){
		String mesAnoMeta = Converte.calendarToString(metaMensal.getMesAnoMeta());
		double valor = metaMensal.getValor();
		double alerta = metaMensal.getAlerta();
		
		String comandoInsercao = "INSERT INTO meta_mensal VALUES";
		try{
			if(!exists(metaMensal.getMesAnoMeta(), descricaoCategoria)){//Verifica se existe uma meta mensal com a mesma data e descrição de categoria;
				int idCategoria = getId(descricaoCategoria);
				
				String comandoSql = comandoInsercao + "(" + idCategoria + ",\'" + mesAnoMeta + "\'," + valor + "," + alerta + ")";
				try {
					this.executaUpdate(comandoSql);
				} catch (SQLException e) {
					e.printStackTrace();
					JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
					return BancoDeDados.RESULTADO_ERRO_BANCO_DADOS;
				}
			}
			else
				return BancoDeDados.RESULTADO_ERRO_REGISTRO_DUPLICADO;// MetaMensal com a mesma data e categoria.
		}
		catch(SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
			return BancoDeDados.RESULTADO_ERRO_BANCO_DADOS;
		}
		
		return BancoDeDados.RESULTADO_SUCESSO;
	}//inserir
	
	/**
	 * Atualiza os dados da metaMensal no banco de dados.
	 * @param novaMetaMensal <code>MetaMensal</code> que será a nova MetaMensal no banco de dados.
	 * @param mesAnoMeta <code>Calendar</code> que será sobrescrita no banco de dados.
	 * @param descricaoCategoria <code>String</code> com a descrição da categoria no banco de dados.
	 * @return <code>true</code> se os dados foram atualizados, <code>false</code> em caso constrário.
	 */
	public boolean atualizarDados(MetaMensal novaMetaMensal, Calendar mesAnoMeta, String descricaoCategoria){
		//verifica se existe a MetaMensal
		try {
			if(!exists(mesAnoMeta, descricaoCategoria))
				return false;
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		int id = 0;
		try {
			id = getId(descricaoCategoria);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if (id <= 0){
			JanelaMensagem.mostraMensagemErro(null, "ID da Categoria Invalida");
			return false;
		}
		
		String novoMesAnoMeta = Converte.calendarToString(novaMetaMensal.getMesAnoMeta());
		double novoValor = novaMetaMensal.getValor();
		double novoAlerta = novaMetaMensal.getAlerta();
		
		String comandoSqlTodos = "SELECT * FROM meta_mensal";
		ResultSet resultadoQuery = BANCO_DE_DADOS_PF.executaQuery(comandoSqlTodos);
		try {
			while(resultadoQuery.next()){
				int idCategoria = resultadoQuery.getInt("idCategoria");
				Calendar mesAnoBD = Converte.stringToCalendar(resultadoQuery.getString("mesAnoMeta"));
				
				String mes = String.valueOf(mesAnoMeta.get(Calendar.MONTH));
				String ano = String.valueOf(mesAnoMeta.get(Calendar.YEAR));
				String mesBD = String.valueOf(mesAnoBD.get(Calendar.MONTH));
				String anoBD = String.valueOf(mesAnoBD.get(Calendar.YEAR));
				
				if(mes.equals(mesBD) && ano.equals(anoBD) && id == idCategoria){
					novoMesAnoMeta = Converte.calendarToString(mesAnoBD);
					break;
				}
			}//while
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		
		String comandoUpdate = "UPDATE meta_mensal SET ";
		String clausulaWhere = " WHERE idCategoria=" + id + " AND mesAnoMeta=\'" + novoMesAnoMeta + "\'";
		
		String comandoSql = comandoUpdate + "valor=" + novoValor + ", alerta=" + novoAlerta + clausulaWhere;
		
		try {
			this.executaUpdate(comandoSql);
		} catch (SQLException e) {
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Exclui uma metaMensal no banco de dados.
	 * @param mesAnoMeta <code>Calendar</code> com a data da <code>MetaMensal</code> a ser excluida.
	 * @param descricaoCategoria <code>String</code> com a descrição da <code>Categoria</code> da <code>MetaMensal</code> a ser excluida.
	 * @return <code>true</code> se os dados foram removidos, <code>false</code> em caso constrário.
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	/*public boolean excluir(Calendar mesAnoMeta, String descricaoCategoria) throws SQLException{
		//verifica se existe a MetaMensal
		if(!exists(mesAnoMeta, descricaoCategoria))
			return false;
		
		int id = 0;
		try {
			id = getId(descricaoCategoria);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if (id <= 0){
			JanelaMensagem.mostraMensagemErro(null, "ID da Categoria Invalida");
			return false;
		}
		
		this.abreConexao();
		
		String excluirMesAnoMeta = Converte.calendarToString(mesAnoMeta);
		
		String comandoSql = "DELETE FROM meta_mensal WHERE idCategoria=" + id + " AND mesAnoMeta=\'" + excluirMesAnoMeta + "\'";
		
		this.executaUpdate(comandoSql);
		
		this.fechaConexao();
		
		return true;
	}*/
	
	/**
	 * Verifica se existe uma MetaMensal com a data e descricao indicada no banco de dados.
	 * @param mesAnoMeta <code>Calendar</code> com a data da MetaMensal a ser pesquisa.
	 * @param descricaoCategoria <code>String</code> com a descricao da Categoria associada a MetaMensal a ser pesquisada.
	 * @return <code>true</code> se a categoria existe, <code>false</code> em caso constrário.
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	public boolean exists(Calendar mesAnoMeta, String descricaoCategoria) throws SQLException{
		this.abreConexao();
		
		int id = 0;
		try {
			id = getId(descricaoCategoria);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if (id <= 0){
			return false;
		}
		
		String comandoSql = "SELECT * FROM meta_mensal";
		ResultSet resultadoQuery = BANCO_DE_DADOS_PF.executaQuery(comandoSql);
		
		try {
			while(resultadoQuery.next()){
				int idCategoria = resultadoQuery.getInt("idCategoria");
				Calendar mesAnoBD = Converte.stringToCalendar(resultadoQuery.getString("mesAnoMeta"));
				
				String mes = String.valueOf(mesAnoMeta.get(Calendar.MONTH));
				String ano = String.valueOf(mesAnoMeta.get(Calendar.YEAR));
				String mesBD = String.valueOf(mesAnoBD.get(Calendar.MONTH));
				String anoBD = String.valueOf(mesAnoBD.get(Calendar.YEAR));
				
				if(mes.equals(mesBD) && ano.equals(anoBD) && id == idCategoria){
					this.fechaConexao();
					return true;
				}
			}//while
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		
		this.fechaConexao();
		
		return  false;
	}
	
	/**
	 * Pesquisa categoria pela descricao
	 * @param descricao <code>String</code> com a descrição da <code>Categoria</code> a ser pesquisada.
	 * @return {@code List<Categoria>} com as categorias que tem na descrição a descrição especificado
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	/*public static List<Categoria> pesquisar(String descricao) throws SQLException{
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		BANCO_DE_DADOS_PF.abreConexao();
		
		String novaDescricao = BancoDeDados.substituiAspasSimplesPorUmaValidaNoBD(descricao);
		String comandoSql = "SELECT * FROM categoria WHERE descricao LIKE \'%" + novaDescricao + "%\'";
		ResultSet resultadoQuery = BANCO_DE_DADOS_PF.executaQuery(comandoSql);
		
		try {
			//resultSet posiciona o cursor antes da primeira linha, entao o next() abaixo ja o coloca na primeira linha, caso haja
			while(resultadoQuery.next()){
				novaDescricao = resultadoQuery.getString("descricao");
				
				categorias.add(new Categoria(novaDescricao));
			}//while
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		
		BANCO_DE_DADOS_PF.fechaConexao();
		return categorias;
	}//pesquisar*/
	
	/**
	 * Retorna todas as entradas da tabela meta_mensal.
	 * @return {@code List<MetaMensal>} com todas as metas mensais da tabela
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	public static List<MetaMensal> todasAsMetasMensais() throws SQLException{
		List<MetaMensal> metasMensais = new ArrayList<MetaMensal>();
		
		BANCO_DE_DADOS_PF.abreConexao();
		
		String comandoSql = "SELECT * FROM meta_mensal";
		ResultSet resultadoQuery = BANCO_DE_DADOS_PF.executaQuery(comandoSql);
		
		while(resultadoQuery.next()){
			int idCategoria = resultadoQuery.getInt("idCategoria");
			Calendar mesAnoMeta = Converte.stringToCalendar( resultadoQuery.getString("mesAnoMeta") );
			double valor = resultadoQuery.getDouble("valor");
			double alerta = resultadoQuery.getDouble("alerta");
			
			metasMensais.add(new MetaMensal(idCategoria, mesAnoMeta, valor, alerta));
		}//while
		
		BANCO_DE_DADOS_PF.fechaConexao();
		return metasMensais;
	}
	
	/**
	 * Retorna todas as entradas do mês e ano da tabela meta_mensal.
	 * @param mesAno <code>Calendar</code> com o mês e ano selecionado.
	 * @return {@code List<MetaMensal>} com todas as metas mensais do mês e ano da tabela
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	public static List<MetaMensal> metaMensalDoMesAno(Calendar mesAno) throws SQLException{
		List<MetaMensal> metasMensal = new ArrayList<MetaMensal>();
		BANCO_DE_DADOS_PF.abreConexao();
		
		String comandoSql = "SELECT * FROM meta_mensal";
		ResultSet resultadoQuery = BANCO_DE_DADOS_PF.executaQuery(comandoSql);
		
		try {
			while(resultadoQuery.next()){
				int idCategoria = resultadoQuery.getInt("idCategoria");
				Calendar mesAnoMeta = Converte.stringToCalendar(resultadoQuery.getString("mesAnoMeta"));
				double valor = resultadoQuery.getDouble("valor");
				double alerta = resultadoQuery.getDouble("alerta");
				
				String mes = String.valueOf(mesAno.get(Calendar.MONTH));
				String ano = String.valueOf(mesAno.get(Calendar.YEAR));
				String mesBD = String.valueOf(mesAnoMeta.get(Calendar.MONTH));
				String anoBD = String.valueOf(mesAnoMeta.get(Calendar.YEAR));
				
				if(mes.equals(mesBD) && ano.equals(anoBD)){
					metasMensal.add(new MetaMensal(idCategoria, mesAnoMeta, valor, alerta));
				}
			}//while
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		
		BANCO_DE_DADOS_PF.fechaConexao();
		return metasMensal;
	}
	
	/**
	 * Retorna a entrada da tabela com id e data iguais ao do argumento.
	 * @param idCategoria <code>int</code> com o id da <code>Categoria</code> associada a <code>MetaMensal</code>a ser pesquisada.
	 * @param mesAno <code>Calendar</code> com a data da <code>MetaMensal</code> a ser pesquisada.
	 * @return metaMensal<code>MetaMensal</code> com a meta mensal
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	public static MetaMensal pesquisaMetaMensal(int idCategoria, Calendar mesAno) throws SQLException{
		MetaMensal metaMensal = new MetaMensal();
		
		BANCO_DE_DADOS_PF.abreConexao();
		
		String comandoSql = "SELECT * FROM meta_mensal";
		ResultSet resultadoQuery = BANCO_DE_DADOS_PF.executaQuery(comandoSql);
		
		while(resultadoQuery.next()){
			int id = resultadoQuery.getInt("idCategoria");
			Calendar mesAnoMeta = Converte.stringToCalendar( resultadoQuery.getString("mesAnoMeta") );
			double valor = resultadoQuery.getDouble("valor");
			double alerta = resultadoQuery.getDouble("alerta");
			
			String mes = String.valueOf(mesAno.get(Calendar.MONTH));
			String ano = String.valueOf(mesAno.get(Calendar.YEAR));
			String mesBD = String.valueOf(mesAnoMeta.get(Calendar.MONTH));
			String anoBD = String.valueOf(mesAnoMeta.get(Calendar.YEAR));
			
			if(mes.equals(mesBD) && ano.equals(anoBD) && id == idCategoria){
				metaMensal.setValor(valor);
				metaMensal.setMesAnoMeta(mesAnoMeta);
				metaMensal.setAlerta(alerta);
				break;
			}
		}//while
		
		BANCO_DE_DADOS_PF.fechaConexao();
		return metaMensal;
	}

	/**
	 *   Retorna o id da categoria associada a MetaMensal no banco de dados
	 * @param descricao <code>String</code> com a descrição da categoria
	 * @return <code>int</code> com o id da categoria no banco de dados, caso não encontre retorna <code>0</code>
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	private int getId(String descricao) throws SQLException{
		int id = 0;
		
		this.abreConexao();
		String comandoSql = "SELECT idCategoria FROM categoria WHERE descricao=\'" + descricao + "\'";
		ResultSet resultadoQuery = this.executaQuery(comandoSql);
		
		if(resultadoQuery.next())
			id = resultadoQuery.getInt(1); //valor da coluna um, unica coluna
		
		this.fechaConexao();
		
		return id;
	}
	
	/**
	 *   Retorna a primeira data da MetaMensal no banco de dados
	 * @return <code>Calendar</code> com a data da primeira meta mensal no banco de dados, caso não encontre retorna <code>null</code>
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	public Calendar getPrimeiraData() throws SQLException{
		Calendar data = null;
		
		this.abreConexao();
		String comandoSql = "SELECT TOP 1 mesAnoMeta FROM meta_mensal";
		ResultSet resultadoQuery = this.executaQuery(comandoSql);
		
		if(resultadoQuery.next())
			data = Converte.stringToCalendar(resultadoQuery.getString("mesAnoMeta")); //valor da coluna um, unica coluna
		
		this.fechaConexao();
		
		return data;
	}
	
	/**
	 *   Retorna a primeira data da MetaMensal no banco de dados
	 * @return {@code List<Calendar>} com todas as datas mensais do mês e ano da tabela
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	public List<Calendar> getDatasMesAno() throws SQLException{
		List<Calendar> datas = new ArrayList<>();
		Calendar data = null;
		
		this.abreConexao();
		String comandoSql = "SELECT mesAnoMeta FROM meta_mensal";
		ResultSet resultadoQuery = this.executaQuery(comandoSql);
		
		while(resultadoQuery.next()){
			data = Converte.stringToCalendar(resultadoQuery.getString("mesAnoMeta"));
			boolean mesExistente = false;
			
			if(datas.size() == 0 && data != null)
				datas.add(data);
			
			for(int i = 0; i < datas.size(); i++){
				if(datas.get(i).get(Calendar.MONTH) == data.get(Calendar.MONTH) && datas.get(i).get(Calendar.YEAR) == data.get(Calendar.YEAR) ){
					System.out.println("true");
					mesExistente = true;
				}
			}
			if(!mesExistente){
				datas.add(data);
			}
		}
		
		this.fechaConexao();
		
		return datas;
	}
}//class MetaMensalDAO
