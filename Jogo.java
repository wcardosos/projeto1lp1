
/**
 * Armazena o tabuleiro e responsavel por posicionar as pecas.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
import java.util.ArrayList;
import java.util.Random;

public class Jogo {

    private Tabuleiro tabuleiro;
    private boolean vez;

    public Jogo() {
        tabuleiro = new Tabuleiro();
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
                            peca = new Peca(casa, Peca.PEDRA_BRANCA);
                        }
                    } else {
                        if(x % 2 != 0) {
                            casa = tabuleiro.getCasa(x,y);
                            peca = new Peca(casa, Peca.PEDRA_BRANCA);
                        }
                    }
                } else if(y > 4) {
                    if(y % 2 == 0) {
                        if(x % 2 == 0) {
                            casa = tabuleiro.getCasa(x,y);
                            peca = new Peca(casa, Peca.PEDRA_VERMELHA);
                        }
                    } else {
                        if(x % 2 != 0) {
                            casa = tabuleiro.getCasa(x,y);
                            peca = new Peca(casa, Peca.PEDRA_VERMELHA);
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
        
        verificaEliminacao(peca, origemX, origemY);
        
        
        if(peca.existeEliminacao()) {
            if(peca.verificaExistenciaPosicao(destinoX, destinoY)) {
                peca.mover(tabuleiro.getCasa(destinoX, destinoY));
                eliminarPecas(origemX, origemY, destinoX, destinoY);
                setVez();
            }
        } else {
            if(origemX - destinoX == 1 || origemX - destinoX == -1) {
                if(peca.getTipo() == 0 && (destinoY - 1 == origemY)) {
                    if(tabuleiro.getCasa(destinoX, destinoY).getPeca() == null) {
                        peca.mover(tabuleiro.getCasa(destinoX, destinoY));
                        setVez();
                    }
                } else if(peca.getTipo() == 2 && (destinoY + 1 == origemY)) {
                    if(tabuleiro.getCasa(destinoX, destinoY).getPeca() == null) {
                        peca.mover(tabuleiro.getCasa(destinoX, destinoY));
                        setVez();
                    }
                }
            }
        }
        
        peca.limpaPosicoesPossiveis();
    }
    
    public void eliminarPecas(int origemX, int origemY, int destinoX, int destinoY) {
        Peca pecaOrigem = tabuleiro.getCasa(origemX, origemY).getPeca();
        int x = -1, y = -1;
        
        if(destinoX > origemX) {
            x = origemX + 1;
        } else if (destinoX < origemX) {
            x = origemX - 1;
        }
        
        if (destinoY > origemY) {
            y = origemY + 1;
        } else if(destinoY < origemY) {
            y = origemY - 1;
        }
        
        tabuleiro.getCasa(x,y).removerPeca();
    }
    
    public void verificaEliminacao(Peca peca, int origemX, int origemY) {
        Casa casa1, casa2;
        if(verificaLimite(origemX - 1) && verificaLimite(origemY + 1) && verificaLimite(origemX - 2) && verificaLimite(origemY + 2)) {
            casa1 = tabuleiro.getCasa(origemX - 1, origemY + 1);
            casa2 = tabuleiro.getCasa(origemX - 2, origemY + 2);
            peca.analisaPosicoesPossiveis(casa1, casa2); //ESQUERDA SUPERIOR
        }
        
        if(verificaLimite(origemX + 1) && verificaLimite(origemY + 1) && verificaLimite(origemX + 2) && verificaLimite(origemY + 2)) {
            casa1 = tabuleiro.getCasa(origemX + 1, origemY + 1);
            casa2 = tabuleiro.getCasa(origemX + 2, origemY + 2);
            peca.analisaPosicoesPossiveis(casa1, casa2); //DIREITA SUPERIOR
        }
        
        if(verificaLimite(origemX - 1) && verificaLimite(origemY - 1) && verificaLimite(origemX - 2) && verificaLimite(origemY - 2)) {
            casa1 = tabuleiro.getCasa(origemX - 1, origemY - 1);
            casa2 = tabuleiro.getCasa(origemX - 2, origemY - 2);
            peca.analisaPosicoesPossiveis(casa1, casa2); //ESQUERDA INFERIOR
        }
        
        if(verificaLimite(origemX + 1) && verificaLimite(origemY - 1) && verificaLimite(origemX + 2) && verificaLimite(origemY - 2)) {
            casa1 = tabuleiro.getCasa(origemX + 1, origemY - 1);
            casa2 = tabuleiro.getCasa(origemX + 2, origemY - 2);
            peca.analisaPosicoesPossiveis(casa1, casa2); //DIREITA INFERIOR
        }
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
        int inicio = r.nextInt(2);
        
        if (inicio == 1) {
            vez = true;
        } else {
            vez = false;
        }
    }
    
    public void setVez() {
        vez = !vez;
    }
    
    public boolean getVez() {
        return vez;
    }
    
    /**
     * @return o Tabuleiro em jogo.
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
}
