-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 29/10/2024 às 03:56
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
-- Estrutura para tabela `ganhos`
--

CREATE TABLE `ganhos` (
  `id` decimal(38,0) NOT NULL,
  `ganho` varchar(30) NOT NULL,
  `anotacao` varchar(200) NOT NULL DEFAULT 'Sem anotação',
  `valor` decimal(38,2) DEFAULT NULL,
  `criado` timestamp NOT NULL DEFAULT current_timestamp(),
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `ganhos`
--

INSERT INTO `ganhos` (`id`, `ganho`, `anotacao`, `valor`, `criado`, `user_id`) VALUES
(153, 'Salário', 'Valor mensal do meu salário', 1490.00, '2024-10-27 21:12:13', NULL),
(252, 'Acréscimos do salário', '', 189.00, '2024-10-28 23:32:34', NULL);

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `ganhos`
--
ALTER TABLE `ganhos`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
