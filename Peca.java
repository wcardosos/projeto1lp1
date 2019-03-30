
/**
 * Representa uma Pe�a do jogo.
 * Possui uma casa e um tipo associado.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */

import java.util.ArrayList;
import java.util.Iterator;

public class Peca {

    public static final int PEDRA_BRANCA = 0;
    public static final int DAMA_BRANCA = 1;
    public static final int PEDRA_VERMELHA = 2;
    public static final int DAMA_VERMELHA = 3;

    private Casa casa;
    private int tipo;
    private boolean dama;
    private ArrayList<Casa> posicoesPossiveis;

    public Peca(Casa casa, int tipo) {
        this.casa = casa;
        this.tipo = tipo;
        dama = false;
        posicoesPossiveis = new ArrayList();
        casa.colocarPeca(this);
    }
    
    /**
     * Movimenta a peca para uma nova casa.
     * @param destino nova casa que ira conter esta peca.
     */
    public void mover(Casa destino) {
        casa.removerPeca();
        destino.colocarPeca(this);
        casa = destino;
    }

    /**
     * Valor    Tipo
     *   0   Branca (Pedra)
     *   1   Branca (Dama)
     *   2   Vermelha (Pedra)
     *   3   Vermelha (Dama)
     * @return o tipo da peca.
     */
    public int getTipo() {
        return tipo;
    }
    
    public void setDama() {
        dama = true;
        tipo++;
    }
    
    public boolean getDama() {
        return dama;
    }
    
    public boolean existeEliminacao() {
        if(posicoesPossiveis.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public void adicionaPosicao(Casa casa) {
        posicoesPossiveis.add(casa);
    }
    
    public boolean verificaExistenciaPosicao(int x, int y) {
        Casa casaOrigem = new Casa(x,y);
        for(Casa casa : posicoesPossiveis) {
            if((casa.getX() == casaOrigem.getX()) && casa.getY() == casaOrigem.getY()) {
                return true;
            }
        }
        
        return false;
    }
    
    public void limpaPosicoesPossiveis() {      //usar cada vez que a lista de posiçoes for utilizada
        if(posicoesPossiveis.size() > 0) {
            posicoesPossiveis.clear();
        }
    }
    
    public void analisaPosicoesPossiveis(Casa casaPecaAdversaria, Casa casaEliminacao) {
        //casaPecaAdversaria = origemX - 1 e origemY + 1
        //casaEliminacao = origem - 2 e origemY + 2
        //continuar com as verificações em Jogo
        //fazer verificaLimite já em Jogo e jogar o argumento Casa já verificado
        //limpaPosicoesPossiveis() CHAMAR APÓS A MOVIMENTAÇÃO CHEGAR AO FINAL
        
        if(tipo == 0) {
              if(casaPecaAdversaria.getPeca() != null && (casaPecaAdversaria.getPeca().getTipo() == 2 || casaPecaAdversaria.getPeca().getTipo() == 3)) {
                  if(casaEliminacao.getPeca() == null) {
                      adicionaPosicao(casaEliminacao);
                  }
                   
              }
        } else if(tipo == 2) {
             if(casaPecaAdversaria.getPeca() != null && (casaPecaAdversaria.getPeca().getTipo() == 0 || casaPecaAdversaria.getPeca().getTipo() == 1)) {
                 if(casaEliminacao.getPeca() == null) {
                     adicionaPosicao(casaEliminacao);
                 }    
             }
        }
    }
}
