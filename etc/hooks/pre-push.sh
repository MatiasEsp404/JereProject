#!/bin/bash

echo "---------------------------------------------"
echo "[INFO] Iniciando CheckStyle"
echo "---------------------------------------------"
string=$(mvn checkstyle:checkstyle)
if [[ $string == *WARN* ]]
then
  echo " "
  echo "El codigo no cumple con las reglas de estilo: commit cancelado.";
  echo "Para mas informacion ingresa 'mvn checkstyle:checkstyle'"
  echo " "
  echo "---------------------------------------------"
  echo "[INFO] CheckStyle finalizado"
  echo "---------------------------------------------"
  exit 1
fi
echo " "
echo "El codigo cumple con las reglas de estilo.";
echo " "
echo "---------------------------------------------"
echo "[INFO] CheckStyle finalizado"
echo "---------------------------------------------"
exit 0