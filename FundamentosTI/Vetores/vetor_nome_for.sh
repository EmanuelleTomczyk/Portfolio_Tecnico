# Autor: Emanuelle L. Tomczyk 
# Descrição: Vetor utilizando for.

#!/bin/bash 
vetor[0]="Flora"
vetor[1]="Agnes"
vetor[2]="Aurora"

tam_vetor=${#vetor[@]}

for nome in ${vetor[@]}
do
      echo "Oi, eu sou $nome"
done
