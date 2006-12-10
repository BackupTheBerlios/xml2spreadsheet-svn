#!/bin/sh


for i in src/test/resources/*.xml
do
  # xmllint --dtdvalid src/main/resources/org/wiztools/xml2spreadsheet/xml2spreadsheet.dtd --noout ${i}
  xmllint --schema src/main/resources/org/wiztools/xml2spreadsheet/xml2spreadsheet.xsd --noout ${i}
  echo
done
