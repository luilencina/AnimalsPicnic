# AnimalsPicnic

Trabalho 3 de Projeto e Otimização de Algoritmos - PUCRS
2022/2 - Professor João Batista


### Escopo do trabalho
Você deve esrever um algoritmo **baseado em backtraking que leia a configuração do jogo via linha de comando** (como os casos que estão ao lado) e esreva como saída um inteiro para cada caso, informando quantas maneiras possíveis existem de posicionar os bichinhos. 

A entrada tem o seguinte formato:

- O número *n* de lajotas dando as dimensões do piso;
- O número *p* de porquinhos;
- O número *g* de galinhas;

Regras:

- O plano é colocar os porquinhos e as galinhas, um em cada lajota do piso;
- O piso é quadrado e tem *n × n* lajotas;
- Todos os porquinhos e galinhas devem fazer parte do jogo;
- Um bicho só poderá ver os bichos que estão na mesma *linha*, *coluna* ou *diagonais*;
- Um bicho só deve ver bichos da mesma espécie que ele.

Seu programa deve pegar os três números pela linha de comando:
```
java porquinhos 5 3 5 // Original: 5x5, 3 porquinhos, 5 galinhas
java porquinhos 6 4 2
java porquinhos 10 5 5
```