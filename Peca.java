
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
    private int pecasParaEliminar;
    private ArrayList<Casa> posicoesPossiveis;

    public Peca(Casa casa, int tipo) {
        this.casa = casa;
        this.tipo = tipo;
        dama = false;
        pecasParaEliminar = 0;
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
    
    public void adicionaPecaParaEliminar() {
        pecasParaEliminar++;
    }
    
    public int getPecaParaEliminar() {
        return pecasParaEliminar;
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
}
