# jGraphicScene
Trilha progressiva de exercícios de Computação Gráfica incluindo checkpoints principais (Trabalhos de Grau)

**Desenvolvido por: Gabriel Lopes Nunes**

## Instruções para rodar
### Compilando o código
O projeto é escrito em Java e usa o Maven para gerenciar as dependências, portanto deve-se o ter instalado, ou, qualquer IDE convencional (IntelliJ IDEA - usei essa, Eclipse, etc.) contém uma versão do Maven *built-in*. No caso do IntelliJ, quando o projeto é aberto (ou importado usando o Maven), o build do Maven já é feito automaticamente e, logo, as dependências (JOGL, JOML, Apache Commons, etc.) estarão disponíveis e já será possível fazer o build do projeto em si para que seja rodado.

### Usando o executável
Dentro da pasta `out/artifacts`, está disponível um executável (*.jar*) para que seja possível uma execução rápida do projeto. Dentro dela também estão incluídos os arquivos `.obj`.

Nesta versão, incluí também um exemplo de arquivo de cena (dentro da pasta `scene`), nela está descrito um sistema solar. Para funcionar corretamente, deve-se mudar o caminho dos arquivos `.obj` e de textura. Quando as texturas são configuradas no `.mtl` o carregamento delas é feito a partir do caminho relativo. Além disso, existe um arquivo de cena que contém comentários para facilitar o entendimento da estrutura de carregamento. (Procure por `cena_explicada.json`).

#### OBJs

Configurei o executável para que ele receba parâmetros posicionais informando os arquivos (caminhos até eles) *.obj* que se deseja que o programa abra. Para executar o programa, o comando é o padrão do Java:

```
java -jar jGraphicScene.jar <path_to_obj1> <path_to_obj2> ... <path_to_objN>
```

#### Cenas

Para executar o programa usando uma cena, deve-se passar um arquivo `.json` depois de informar o parâmetro `-s`:

```
java -jar jGraphicScene.jar -s <path_to_json>
```

## Instruções para usar
O programa conta com alguns atalhos:

|Atalho|Descrição da funcionalidade|
|------|---------------------------|
|`Escape`|Termina o programa
|`Right`, `Left`|Avança ou retrocede pelos modelos especificados nos parâmetros ou cenas carregadas do arquivo `.json`
|`W`, `A`, `S`, `D`|Movimenta a view
|`Space`|Reseta a câmera

*Obs.: a câmera pode ser movimentada usando o mouse e a roda aplica o zoom*