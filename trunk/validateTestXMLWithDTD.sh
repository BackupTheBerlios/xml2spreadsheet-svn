#!/bin/sh


for i in src/test/resources/*.xml
do
  echo == Validating ${i} ==
  xmllint --dtdvalid src/main/resources/org/wiztools/xml2spreadsheet/xml2spreadsheet.dtd --noout ${i}
  echo
done
