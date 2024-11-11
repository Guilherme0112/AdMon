-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 11/11/2024 às 01:03
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `admon`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `contas`
--

CREATE TABLE `contas` (
  `id` decimal(38,0) NOT NULL,
  `conta` varchar(30) NOT NULL,
  `anotacao` varchar(200) NOT NULL DEFAULT 'Sem anotação',
  `valor` decimal(38,2) DEFAULT NULL,
  `vencimento` date NOT NULL,
  `criado` timestamp NOT NULL DEFAULT current_timestamp(),
  `user_email` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `contas`
--

INSERT INTO `contas` (`id`, `conta`, `anotacao`, `valor`, `vencimento`, `criado`, `user_email`) VALUES
(1552, 'Faculdade', 'Valor mensal da faculdade', 335.00, '2024-12-10', '2024-11-10 23:30:38', 'guimendesmen124@gmail.com'),
(1553, 'Uber', 'Valor médio gasto com o Uber', 160.00, '2024-11-30', '2024-11-10 23:31:11', 'guimendesmen124@gmail.com'),
(1554, 'Aluguel', '', 200.00, '2024-12-05', '2024-11-10 23:31:35', 'guimendesmen124@gmail.com');

-- --------------------------------------------------------

--
-- Estrutura para tabela `contas_seq`
--

CREATE TABLE `contas_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `contas_seq`
--

INSERT INTO `contas_seq` (`next_val`) VALUES
(1651);

-- --------------------------------------------------------

--
-- Estrutura para tabela `ganhos`
--

CREATE TABLE `ganhos` (
  `id` decimal(38,0) NOT NULL,
  `ganho` varchar(30) NOT NULL,
  `anotacao` varchar(200) NOT NULL DEFAULT 'Sem anotação',
  `valor` decimal(38,2) DEFAULT NULL,
  `criado` timestamp NOT NULL DEFAULT current_timestamp(),
  `user_email` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `ganhos`
--

INSERT INTO `ganhos` (`id`, `ganho`, `anotacao`, `valor`, `criado`, `user_email`) VALUES
(402, 'Adiantamento Salarial', '', 860.00, '2024-11-10 23:36:59', 'guimendesmen124@gmail.com'),
(403, 'Resto do salário', '', 630.00, '2024-11-10 23:37:23', 'guimendesmen124@gmail.com'),
(404, 'Acréscimos do salário', '', 189.00, '2024-11-10 23:37:33', 'guimendesmen124@gmail.com');

-- --------------------------------------------------------

--
-- Estrutura para tabela `ganhos_seq`
--

CREATE TABLE `ganhos_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `ganhos_seq`
--

INSERT INTO `ganhos_seq` (`next_val`) VALUES
(501);

-- --------------------------------------------------------

--
-- Estrutura para tabela `usuarios`
--

CREATE TABLE `usuarios` (
  `id` decimal(38,0) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nome` varchar(55) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `permissoes` varbinary(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `usuarios`
--

INSERT INTO `usuarios` (`id`, `email`, `nome`, `senha`, `permissoes`) VALUES
(102, 'guimendesmen124@gmail.com', 'Guilherme', '25f9e794323b453885f5181f1b624d0b', NULL);

-- --------------------------------------------------------

--
-- Estrutura para tabela `usuarios_seq`
--

CREATE TABLE `usuarios_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `usuarios_seq`
--

INSERT INTO `usuarios_seq` (`next_val`) VALUES
(201);

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `contas`
--
ALTER TABLE `contas`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `ganhos`
--
ALTER TABLE `ganhos`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
