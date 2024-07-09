# Sistema Conta Corrente - Trabalho Final

## Descrição
Este repositório contém o trabalho final da disciplina de Introdução à Programação, parte da Especialização em Desenvolvimento Ágil de Software da Universidade Federal do Paraná (UFPR). O objetivo do trabalho é a implementação do backend de um sistema bancário simplificado para controle de cadastro de clientes, contas correntes e contas investimento.

## Especificação do Projeto
O projeto consiste na implementação das classes necessárias para que todos os testes unitários fornecidos fiquem funcionando (verdes). Os seguintes recursos foram disponibilizados:

- Diagrama de classes do sistema.
- Projeto no NetBeans com testes unitários escritos.
- Script DDL para criação das tabelas do banco de dados no MySQL.

## Casos de Teste
Os casos de teste estão escritos no JUnit e estão disponíveis no projeto fornecido no Moodle. Existem 42 casos de teste no total. Para atingir a nota máxima, é necessário que 40 casos de teste estejam funcionando simultaneamente. Cada caso de teste verde vale 2,5 pontos.

## Entrega
O trabalho deve ser entregue no Moodle. Cada aluno deve entregar um arquivo ZIP contendo:
- O projeto com as classes implementadas.
- Os casos de testes.
- Um relatório em PDF com evidências dos testes que funcionaram, coletadas do NetBeans em formato de imagem.

## Preparação do Banco de Dados
Para preparar o banco de dados, execute os seguintes passos:
1. Abra o MySQL Command Line.
2. Faça login com o usuário e senha (root do banco).
3. Execute o comando: `source <Caminho completo do arquivo>.`

> Observação: O caminho do arquivo não pode conter espaços ou caracteres especiais. Este comando criará a base de dados `bancorrw` com as 4 tabelas necessárias.

## Estrutura do Projeto
O projeto é composto pelas seguintes partes:
- **Classes de Sistema**: Implementação das classes do sistema conforme o diagrama de classes.
- **Testes Unitários**: Testes escritos em JUnit para verificar a funcionalidade das classes implementadas.
- **Script DDL**: Script para criação das tabelas no banco de dados MySQL.

## Diagrama ER do Banco de Dados
![Diagrama ER](path/to/er-diagram.png)

## Diagrama de Classes
![Diagrama de Classes](path/to/class-diagram.png)

## Licença
Este projeto utiliza código fornecido pelo Professor Rafael Romualdo Wandresen. É necessário obter permissão para qualquer uso além do escopo acadêmico. Para mais informações, entre em contato com o professor.

## Autores
- [Jean Carlos](http://lattes.cnpq.br/1659693816509187)

## Agradecimentos
Agradecemos ao Professor Rafael Romualdo Wandresen pela orientação e pelo material fornecido para a realização deste trabalho.

---

UFPR – Universidade Federal do Paraná  
Setor de Educação Profissional e Tecnológica  
Especialização em Desenvolvimento Ágil de Software  
Introdução à Programação  
Prof. Rafael Romualdo Wandresen
