 
/**
 * Armazena o tabuleiro e responsavel por posicionar as pecas.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
import java.util.ArrayList;
import java.util.Random;

public class Jogo {
    
    private boolean vez;
    private boolean controleEliminacaoSucessiva;
    private Tabuleiro tabuleiro;

    public Jogo() {
        tabuleiro = new Tabuleiro();
        controleEliminacaoSucessiva = false;
        criarPecas();
        jogadorIniciante();
    }
    
    /**
     * Posiciona pe�as no tabuleiro.
     * Utilizado na inicializa�ao do jogo.
     */
    private void criarPecas() {
        Casa casa;
        Peca peca;
        for (int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                if(y < 3) {
                    if(y % 2 == 0) {
                        if(x % 2 == 0) {
                            casa = tabuleiro.getCasa(x,y);
                            peca = new Peca(casa, Peca.PEDRA_BRANCA, 1);
                        }
                    } else {
                        if(x % 2 != 0) {
                            casa = tabuleiro.getCasa(x,y);
                            peca = new Peca(casa, Peca.PEDRA_BRANCA, 1);
                        }
                    }
                } else if(y > 4) {
                    if(y % 2 == 0) {
                        if(x % 2 == 0) {
                            casa = tabuleiro.getCasa(x,y);
                            peca = new Peca(casa, Peca.PEDRA_VERMELHA, -1);
                        }
                    } else {
                        if(x % 2 != 0) {
                            casa = tabuleiro.getCasa(x,y);
                            peca = new Peca(casa, Peca.PEDRA_VERMELHA, -1);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Comanda uma Pe�a na posicao (origemX, origemY) fazer um movimento 
     * para (destinoX, destinoY).
     * 
     * @param origemX linha da Casa de origem.
     * @param origemY coluna da Casa de origem.
     * @param destinoX linha da Casa de destino.
     * @param destinoY coluna da Casa de destino.
     */
    public void moverPeca(int origemX, int origemY, int destinoX, int destinoY) {
        Peca peca = tabuleiro.getCasa(origemX, origemY).getPeca();
        peca.verificaEliminacao(tabuleiro);
        
        if(peca.existeEliminacao()) {
            if(peca.verificaExistenciaPosicao(destinoX, destinoY)) {
                eliminarPeca(peca.casaParaEliminar(tabuleiro.getCasa(destinoX, destinoY)));
                peca.mover(tabuleiro.getCasa(destinoX, destinoY));
                peca = tabuleiro.getCasa(destinoX, destinoY).getPeca();
                peca.eliminacaoSucessiva(tabuleiro);
                setControleEliminacaoSucessiva(peca.getEliminacaoSucessiva());
                
                if(!peca.getEliminacaoSucessiva()) {
                    //verificaPecaEhDama(destinoX, destinoY);
                    setVez();
                }
            }
        } else {
            if(peca.movimentoSimples(tabuleiro.getCasa(destinoX, destinoY))) {
                peca.mover(tabuleiro.getCasa(destinoX, destinoY));
                setVez();
            }
        }
        
        peca.limpaPosicoesPossiveis();
    }
    
    public void eliminarPeca(Casa casaEliminacao) {
        tabuleiro.getCasa(casaEliminacao.getX(), casaEliminacao.getY()).removerPeca();
    }
    
    public void setControleEliminacaoSucessiva(boolean controle) {
        controleEliminacaoSucessiva = controle;
    }
    
    public boolean getControleEliminacaoSucessiva() {
        return controleEliminacaoSucessiva;
    }
    
    public boolean verificaLimite(int pos) {
        if (pos < 0) {
            return false;
        } else if (pos > 7) {
            return false;
        } else {
            return true;
        }
    }
    
    public void jogadorIniciante() {
        Random r = new Random();
        vez = r.nextBoolean();
    }
    
    public void setVez() {
        vez = !vez;
    }
    
    public boolean getVez() {
        return vez;
    }
    
    public void verificaPecaEhDama(int posicaoX, int posicaoY) {
        Dama pecaDama;
        Casa casa = tabuleiro.getCasa(posicaoX, posicaoY);
        
        if(casa.getPeca().getDama()) {
            pecaDama = new Dama(casa, casa.getPeca().getTipo());
            casa.removerPeca();
            casa.colocarPeca(pecaDama);
        }
    }
    
    /**
     * @return o Tabuleiro em jogo.
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
}
