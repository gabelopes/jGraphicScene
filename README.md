# jGraphicScene
Trilha progressiva de exercícios de Computação Gráfica incluindo checkpoints principais (Trabalhos de Grau)

**Desenvolvido por: Gabriel Lopes Nunes**

## Instruções para rodar
### Compilando o código
O projeto é escrito em Java e usa o Maven para gerenciar as dependências, portanto deve-se o ter instalado, ou, qualquer IDE convencional (IntelliJ IDEA - usei essa, Eclipse, etc.) contém uma versão do Maven *built-in*. No caso do IntelliJ, quando o projeto é aberto (ou importado usando o Maven), o build do Maven já é feito automaticamente e, logo, as dependências (JOGL, JOML, Apache Commons, etc.) estarão disponíveis e já será possível fazer o build do projeto em si para que seja rodado.

### Usando o executável
Dentro da pasta `out/artifacts`, está disponível um executável (*.jar*) para que seja possível uma execução rápida do projeto. Dentro dela também estão incluídos os arquivos *.obj*.

Configurei o executável para que ele receba parâmetros posicionais informando os arquivos (caminhos até eles) *.obj* que se deseja que o programa abra. Para executar o programa, o comando é o padrão do Java:

    java -jar jGraphicScene.jar <path_to_obj1> <path_to_obj2> ... <path_to_objN>

## Instruções para usar
O programa conta com uma série de atalhos para que seja possível usar cada funcionalidade, abaixo estão arrolados todos os atalhos existentes:

|Atalho|Descrição da funcionalidade|
|------|---------------------------|
|`Escape`|Termina o programa
|`Right`, `Left`|Avança ou retrocede pelos modelos especificados nos parâmetros
|`W`, `A`, `S`, `D`|Movimenta a view
|`Space`|Reseta a câmera
|`E`, `R`, `T`|Aciona os modos de escala, rotação e translação respectivamente
|`X`, `Y`, `Z`|Aciona os eixos das abscissas, ordendas e cotas para uso com os modos de transformação
|`Up`, `Down`|Realiza as transformações acionadas nos eixos acionados com fatores positivo e negativo respectivamente
|`Backspace`|Limpa todos os modos e os eixos acionados

*Obs.: a câmera pode ser movimentada usando o mouse e a roda aplica o zoom*

## Algumas considerações
* A classe principal, que contém o método `main`, realiza apenas o carregamento dos arquivos *.obj* passados. No entanto, estão disponíveis exemplos de outros usos do código, como a geração de primitivas e formas 2D com cor e transformação, formas 3D compostas numa cena, etc.

* A cena não é totalmente funcional: ainda não é possível iluminar uma cena, nem fazer operações mais complexas com ela. Além disso, por questões estruturais, optou-se por não aplicar a transformação dos objetos individualmente dentro de cenas, pelo menos nessa versão, mesmo que em versões antigas isso era possível.

* Arquivos *.obj* carregados que não contenham vetores normais, serão tratados como objetos não ilumináveis e portanto serão desenhados com o *shader* padrão (`default`) e não com o *shader* de iluminação (`lighting`).

* Quando for usar a escala, não esqueça de acionar todos os eixos, pois podem acontecer problemas na hora de escalar em algum(ns) eixo(s) separado(s), ademais, a escala falha às vezes.