# Projeto Pizzaria
![Java](https://www.vectorlogo.zone/logos/java/java-icon.svg)


## Descrição

Este projeto é uma aplicação Java desenvolvido para a materia de Linguagem De Programação Orientada a Objeto I (LPOOI)

### Premissa

**O projeto consiste em uma pizzaria com um banco de dados "falso" cuja a premissa é a seguinte:**

> Um empreendedor resolveu criar uma pizzaria diferente das que existem no mercado. A sua ideia é vender pizza em
centímetro quadrado, no lugar das tradicionais pequena, média e grande. Além disso, também resolveu inovar na forma da
pizza que pode assumir a forma tradicional circular, um quadrado, ou um triângulo. O preço de cada pizza será definido pela
área em centímetros quadrados, cruzado com o tipo da pizza (dependendo dos ingredientes). Os sabores das pizzas estão
divididos em Simples, Especial e Premium.

### Requisitos 
1. **Tela de Cadastro de Clientes:**
   - Cadastro de clientes com Nome, Sobrenome e Telefone.
   - Listagem de todos os clientes utilizando uma JTable.
   - Atualização dos dados de um cliente existente.
   - Exclusão de um cliente (incluindo seus pedidos associados).
   - Filtro de clientes por Sobrenome, parte do Sobrenome e/ou Telefone.

2. **Tela de Realização de Pedidos:**
   - Pré-requisito: cliente deve estar cadastrado.
   - Escolha de cliente filtrando por Telefone.
   - Listagem e possível atualização dos itens de um pedido existente do cliente.
   - Adição de novos itens ao pedido se o cliente não possuir um pedido registrado.
   - Escolha da forma da pizza (quadrado, círculo ou triângulo).
     - Restrições de dimensões: 
       - Quadrado: lado mínimo 10 cm e máximo 40 cm.
       - Triângulo: lado mínimo 20 cm e máximo 60 cm.
       - Círculo: raio mínimo 7 cm e máximo 23 cm.
   - Escolha de até dois sabores relacionados ao tipo de pizza.
   - Informação da quantidade em centímetros quadrados opcional (mínimo 100 cm², máximo 1600 cm²).
   - Cálculo do preço total da venda durante o processo de pedido.

3. **Tela de Atualização do Preço por Centímetro Quadrado:**
   - Permite a atualização dos preços para diferentes tipos de pizza.

4. **Tela de Cadastro de Sabores de Pizzas:**
   - Permite o cadastro de sabores de pizzas e associação com tipos de pizzas.

5. **Tela de Gerenciamento de Pedidos:**
   - Visualização dos pedidos de todos os clientes e seus estados (aberto, a caminho, entregue).
   - Alteração do estado dos pedidos diretamente na tela.

6. **Validações:**
   - Implementação de validações nos campos das telas para informar usuários sobre entradas inválidas.



