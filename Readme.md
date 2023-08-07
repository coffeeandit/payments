#### Transação com Sucesso.

curl --location 'http://localhost:8080/pagamento' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=AE809DB99CF376388DC11F0662E21ABB' \
--data '{
"quantidade" : 64900,
"cartao": {
"numero" : "4242424242424242",
"mesExpiracao" : 12,
"anoExpiracao" : 2028,
"cvc" : "763"
},
"descricao" : "Maravilhosos cursos da CoffeeAndIT"
}'

#### Transação sem Fundos

curl --location 'http://localhost:8080/pagamento' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=AE809DB99CF376388DC11F0662E21ABB' \
--data '{
"quantidade" : 649000,
"cartao": {
"numero" : "4000000000009995",
"mesExpiracao" : 12,
"anoExpiracao" : 2028,
"cvc" : "763"
},
"descricao" : "Maravilhosos cursos da CoffeeAndIT"
}'

#### Transação sem Autenticação

curl --location 'http://localhost:8080/pagamento' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=AE809DB99CF376388DC11F0662E21ABB' \
--data '{
"quantidade" : 64900,
"cartao": {
"numero" : "4000002500003155",
"mesExpiracao" : 12,
"anoExpiracao" : 2028,
"cvc" : "763"
},
"descricao" : "Maravilhosos cursos da CoffeeAndIT"
}'

