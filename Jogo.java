
/**
 * Armazena o tabuleiro e responsavel por posicionar as pecas.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
import java.util.ArrayList;

public class Jogo {

    private Tabuleiro tabuleiro;

    public Jogo() {
        tabuleiro = new Tabuleiro();
        criarPecas();
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
        analisaPosicoesPossiveis(origemX, origemY);
        
        if(peca.existeEliminacao()) {
            if(peca.verificaExistenciaPosicao(destinoX, destinoY)) {
               peca.mover(tabuleiro.getCasa(destinoX, destinoY));
               eliminarPecas(origemX, origemY, destinoX, destinoY);
               
            }
        } else {
            if(origemX - destinoX == 1 || origemX - destinoX == -1) {
                if(peca.getTipo() == 0 && (destinoY - 1 == origemY)) {
                    if(tabuleiro.getCasa(destinoX, destinoY).getPeca() == null) {
                        peca.mover(tabuleiro.getCasa(destinoX, destinoY));
                    }
                } else if(peca.getTipo() == 2 && (destinoY + 1 == origemY)) {
                    if(tabuleiro.getCasa(destinoX, destinoY).getPeca() == null) {
                        peca.mover(tabuleiro.getCasa(destinoX, destinoY));
                    }
                }
            }
        }
        
    }
    
    public void analisaPosicoesPossiveis(int origemX, int origemY) {
        Casa verificacao;
        Casa casa = tabuleiro.getCasa(origemX, origemY);
        casa.getPeca().limpaPosicoesPossiveis();
        if(verificaLimite(origemX - 1) && verificaLimite(origemY + 1)) {
            verificacao = tabuleiro.getCasa(origemX - 1, origemY + 1);
            if(casa.getPeca().getTipo() == 0) {
                if(verificacao.getPeca() != null && (verificacao.getPeca().getTipo() == 2 || verificacao.getPeca().getTipo() == 3)) {
                    if(verificaLimite(origemX - 2) && verificaLimite(origemY + 2)) {
                        if(tabuleiro.getCasa(origemX - 2, origemY + 2).getPeca() == null) {
                            casa.getPeca().adicionaPosicao(new Casa(origemX - 2, origemY + 2));
                            casa.getPeca().adicionaPecaParaEliminar();
                        }
                    }
                }
            } else if(casa.getPeca().getTipo() == 2) {
                if(verificacao.getPeca() != null && (verificacao.getPeca().getTipo() == 0 || verificacao.getPeca().getTipo() == 1)) {
                    if(verificaLimite(origemX - 2) && verificaLimite(origemY + 2)) {
                        if(tabuleiro.getCasa(origemX - 2, origemY + 2).getPeca() == null) {
                            casa.getPeca().adicionaPosicao(new Casa(origemX - 2, origemY + 2));
                            casa.getPeca().adicionaPecaParaEliminar();
                        }
                    }
                }
            }
        }
        
        if(verificaLimite(origemX + 1) && verificaLimite(origemY + 1)) {
            verificacao = tabuleiro.getCasa(origemX + 1, origemY + 1);
            if(casa.getPeca().getTipo() == 0) {
                if(verificacao.getPeca() != null && (verificacao.getPeca().getTipo() == 2 || verificacao.getPeca().getTipo() == 3)) {
                    if(verificaLimite(origemX + 2) && verificaLimite(origemY + 2)) {
                        if(tabuleiro.getCasa(origemX + 2, origemY + 2).getPeca() == null) {
                            casa.getPeca().adicionaPosicao(new Casa(origemX + 2, origemY + 2));
                            casa.getPeca().adicionaPecaParaEliminar();
                        }
                    }
                }
            } else if(casa.getPeca().getTipo() == 2) {
                if(verificacao.getPeca() != null && (verificacao.getPeca().getTipo() == 0 || verificacao.getPeca().getTipo() == 1)) {
                    if(verificaLimite(origemX + 2) && verificaLimite(origemY + 2)) {
                        if(tabuleiro.getCasa(origemX + 2, origemY + 2).getPeca() == null) {
                            casa.getPeca().adicionaPosicao(new Casa(origemX + 2, origemY + 2));
                            casa.getPeca().adicionaPecaParaEliminar();
                        }
                    }
                }
            }
        }
        
        
        
        if(verificaLimite(origemX - 1) && verificaLimite(origemY - 1)) {
            verificacao = tabuleiro.getCasa(origemX - 1, origemY - 1);
            if(casa.getPeca().getTipo() == 0) {
                if(verificacao.getPeca() != null && (verificacao.getPeca().getTipo() == 2 || verificacao.getPeca().getTipo() == 3)) {
                    if(verificaLimite(origemX - 2) && verificaLimite(origemY - 2)) {
                        if(tabuleiro.getCasa(origemX - 2, origemY - 2).getPeca() == null) {
                            casa.getPeca().adicionaPosicao(new Casa(origemX - 2, origemY - 2));
                            casa.getPeca().adicionaPecaParaEliminar();
                        }
                    }
                }
            } else if(casa.getPeca().getTipo() == 2) {
                if(verificacao.getPeca() != null && (verificacao.getPeca().getTipo() == 0 || verificacao.getPeca().getTipo() == 1)) {
                    if(verificaLimite(origemX - 2) && verificaLimite(origemY - 2)) {
                        if(tabuleiro.getCasa(origemX - 2, origemY - 2).getPeca() == null) {
                            casa.getPeca().adicionaPosicao(new Casa(origemX - 2, origemY - 2));
                            casa.getPeca().adicionaPecaParaEliminar();
                        }
                    }
                }
            }
        }
        
        if(verificaLimite(origemX + 1) && verificaLimite(origemY - 1)) {
            verificacao = tabuleiro.getCasa(origemX + 1, origemY - 1);
            if(casa.getPeca().getTipo() == 0) {
                if(verificacao.getPeca() != null && (verificacao.getPeca().getTipo() == 2 || verificacao.getPeca().getTipo() == 3)) {
                    if(verificaLimite(origemX - 2) && verificaLimite(origemY + 2)) {
                        if(tabuleiro.getCasa(origemX + 2, origemY - 2).getPeca() == null) {
                            casa.getPeca().adicionaPosicao(new Casa(origemX + 2, origemY - 2));
                            casa.getPeca().adicionaPecaParaEliminar();
                        }
                    }
                }
            } else if(casa.getPeca().getTipo() == 2) {
                if(verificacao.getPeca() != null && (verificacao.getPeca().getTipo() == 0 || verificacao.getPeca().getTipo() == 1)) {
                    if(verificaLimite(origemX + 2) && verificaLimite(origemY - 2)) {
                        if(tabuleiro.getCasa(origemX + 2, origemY - 2).getPeca() == null) {
                            casa.getPeca().adicionaPosicao(new Casa(origemX + 2, origemY - 2));
                            casa.getPeca().adicionaPecaParaEliminar();
                        }
                    }
                }
            }
        }
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
    
    public boolean verificaLimite(int pos) {
        if (pos < 0) {
            return false;
        } else if (pos > 7) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * @return o Tabuleiro em jogo.
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
}
