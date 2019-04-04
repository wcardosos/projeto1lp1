
/**
 * Representa uma Pe�a do jogo.
 * Possui uma casa e um tipo associado.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */

import java.util.ArrayList;

public class Peca {

    public static final int PEDRA_BRANCA = 0;
    public static final int DAMA_BRANCA = 1;
    public static final int PEDRA_VERMELHA = 2;
    public static final int DAMA_VERMELHA = 3;

    private Casa casa;
    private int direcao;
    private int tipo;
    private boolean dama;
    private boolean eliminacaoSucessiva;
    private ArrayList<Casa> posicoesPossiveis;

    public Peca(Casa casa, int tipo, int direcao) {
        this.casa = casa;
        this.tipo = tipo;
        this.direcao = direcao;
        dama = false;
        eliminacaoSucessiva = false;
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
        verificaDama();
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
        if(!dama) {
            dama = true;
            tipo++;
        }
        else {
            dama = false;
            tipo--;
        }
        
        direcao = -direcao;
        
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
    
    
    public boolean movimentoSimples(Casa destino) {
        boolean podeMover = false;
        if(destino.getPeca() == null) {
            if(destino.getY() == casa.getY() + direcao) {
                if(destino.getX() == casa.getX() + 1 || destino.getX() == casa.getX() - 1) {
                    podeMover = true;
                }
            }
        }
        
        return podeMover;
    }
    
    public void limpaPosicoesPossiveis() {      //usar cada vez que a lista de posiçoes for utilizada
        if(posicoesPossiveis.size() > 0) {
            posicoesPossiveis.clear();
        }
    }
    
    public void analisaEliminacoes(Casa casaPecaAdversaria, Casa casaEliminacao) {
        if(tipo < 2) {
              if(casaPecaAdversaria.getPeca() != null && (casaPecaAdversaria.getPeca().getTipo() == 2 || casaPecaAdversaria.getPeca().getTipo() == 3)) {
                  if(casaEliminacao.getPeca() == null) {
                      adicionaPosicao(casaEliminacao);
                  }
                   
              }
        } else {
             if(casaPecaAdversaria.getPeca() != null && (casaPecaAdversaria.getPeca().getTipo() == 0 || casaPecaAdversaria.getPeca().getTipo() == 1)) {
                 if(casaEliminacao.getPeca() == null) {
                     adicionaPosicao(casaEliminacao);
                 }    
             }
        }
        
    }
    
    public void verificaEliminacao(Tabuleiro tabuleiro) {
        int origemX = casa.getX();
        int origemY = casa.getY();
        Casa casaPecaAdversaria, casaLivre;
        
        if(limite(origemX - 1) && limite(origemY + 1) && limite(origemX - 2) && limite(origemY + 2)) {
            casaPecaAdversaria = tabuleiro.getCasa(origemX - 1, origemY + 1);
            casaLivre = tabuleiro.getCasa(origemX - 2, origemY + 2);
            analisaEliminacoes(casaPecaAdversaria, casaLivre);
        }
        
        if(limite(origemX + 1) && limite(origemY + 1) && limite(origemX + 2) && limite(origemY + 2)) {
            casaPecaAdversaria = tabuleiro.getCasa(origemX + 1, origemY + 1);
            casaLivre = tabuleiro.getCasa(origemX + 2, origemY + 2);
            analisaEliminacoes(casaPecaAdversaria, casaLivre);
        }
        
        if(limite(origemX - 1) && limite(origemY - 1) && limite(origemX - 2) && limite(origemY - 2)) {
            casaPecaAdversaria = tabuleiro.getCasa(origemX - 1, origemY - 1);
            casaLivre = tabuleiro.getCasa(origemX - 2, origemY - 2);
            analisaEliminacoes(casaPecaAdversaria, casaLivre);
        }
        
        if(limite(origemX + 1) && limite(origemY - 1) && limite(origemX + 2) && limite(origemY - 2)) {
            casaPecaAdversaria = tabuleiro.getCasa(origemX + 1, origemY - 1);
            casaLivre = tabuleiro.getCasa(origemX + 2, origemY - 2);
            analisaEliminacoes(casaPecaAdversaria, casaLivre);
        }
    }
    
    public Casa casaParaEliminar(Casa destino) { // CHAMAR ANTES DE MOVER A PECA
        int posicaoXEliminacao = -1, posicaoYEliminacao = -1;
        Casa casaEliminacao;
        
        if(destino.getX() > casa.getX()) {
            posicaoXEliminacao = casa.getX() + 1;
        }
        else if(destino.getX() < casa.getX()) {
            posicaoXEliminacao = casa.getX() - 1;
        }
        
        if(destino.getY() > casa.getY()) {
            posicaoYEliminacao = casa.getY() + 1;
        } else if(destino.getY() < casa.getY()) {
            posicaoYEliminacao = casa.getY() - 1;
        }
        
        return casaEliminacao = new Casa(posicaoXEliminacao, posicaoYEliminacao);
    }
    
    public void eliminacaoSucessiva(Tabuleiro tabuleiro){
        limpaPosicoesPossiveis();
        
        verificaEliminacao(tabuleiro);
        setEliminacaoSucessiva();
    }
    
    public void setEliminacaoSucessiva() {
        eliminacaoSucessiva = existeEliminacao();
    }
    
    public boolean getEliminacaoSucessiva() {
        return eliminacaoSucessiva;
    }
    
    public int getDirecao() {
        return direcao;
    }
    
    public boolean verificaCasaEliminacao(Casa destinoEliminacao) {
        for(Casa casa : posicoesPossiveis) {
            if(casa.equals(destinoEliminacao)) {
                return true;
            }
        }
        
        return false;
    }
    
    public void verificaDama() {
        if((tipo == 0 || tipo == 3) && casa.getY() == 7) {
            setDama();
        }
        else if((tipo == 2 || tipo == 1) && casa.getY() == 0) {
            setDama();
        }
    }
    
    // RETORNA TRUE SE NÃO ESTIVER NO LIMITE
    public boolean limite(int pos) {
        if (pos < 0) {
            return false;
        } else if (pos > 7) {
            return false;
        } else {
            return true;
        }
    }
}
