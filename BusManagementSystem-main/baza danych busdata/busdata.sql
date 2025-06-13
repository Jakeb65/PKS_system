
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `busdata`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_polish_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(1, 'Jakub', '12345'),
(2, 'Dawid', '12345'),
(3, 'Konrad', '12345'),
(4, 'Bartek', '12345');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `bus`
--

CREATE TABLE `bus` (
  `id` int(100) NOT NULL,
  `bus_id` int(100) NOT NULL,
  `location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_polish_ci NOT NULL,
  `status` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_polish_ci NOT NULL,
  `price` double NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bus`
--

INSERT INTO `bus` (`id`, `bus_id`, `location`, `status`, `price`, `date`) VALUES
(1, 1, 'Krakow', 'Available', 30, '2025-06-07'),
(2, 2, 'Warszawa', 'Available', 41, '2025-06-13'),
(3, 3, 'Kielce', 'Available', 10, '2025-06-06'),
(4, 4, 'Wroclaw', 'Available', 55, '2025-06-13'),
(8, 5, 'Konskie', 'Available', 34, '2025-06-19'),
(9, 6, 'Lodz', 'Available', 38, '2025-06-21'),
(11, 7, 'Rzeszow', 'Available', 65, '2025-06-14');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `customer`
--

CREATE TABLE `customer` (
  `id` int(100) NOT NULL,
  `customer_id` int(100) NOT NULL,
  `firstName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_polish_ci NOT NULL,
  `lastName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_polish_ci NOT NULL,
  `gender` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_polish_ci NOT NULL,
  `phoneNumber` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_polish_ci NOT NULL,
  `bus_id` int(100) NOT NULL,
  `location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_polish_ci NOT NULL,
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_polish_ci NOT NULL,
  `seatNum` int(100) NOT NULL,
  `total` double NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `customer_id`, `firstName`, `lastName`, `gender`, `phoneNumber`, `bus_id`, `location`, `type`, `seatNum`, `total`, `date`) VALUES
(4, 4, 'Konrad', 'Gliński', 'Male', '222222222', 2, 'Warszawa', 'First Class', 1, 141, '2025-06-13'),
(6, 3, 'Jakub ', 'Pędziwilk', 'Male', '963741852', 3, 'Kielce', 'First Class', 3, 110, '2025-06-15'),
(7, 4, 'Dawid', 'Nowak', 'Male', '555555555', 1, 'Krakow', 'Economy Class', 15, 30, '2025-06-07'),
(8, 5, 'Bartłomiej', 'Nowak', 'Male', '777777777', 3, 'Kielce', 'First Class', 8, 110, '2025-06-06'),
(11, 5, 'Jan', 'Kopytko', 'Male', '543765876', 1, 'Krakow', 'Economy Class', 4, 30, '2025-06-21'),
(12, 6, 'Damian', 'Miazga', 'Female', '980876765', 3, 'Kielce', 'Economy Class', 9, 10, '2025-06-19');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `customer_receipt`
--

CREATE TABLE `customer_receipt` (
  `id` int(100) NOT NULL,
  `customer_id` int(100) NOT NULL,
  `total` double NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer_receipt`
--

INSERT INTO `customer_receipt` (`id`, `customer_id`, `total`, `date`) VALUES
(1, 1, 140, '2025-06-12'),
(2, 2, 10, '2025-06-13'),
(3, 3, 41, '2025-06-07'),
(4, 4, 141, '2025-06-13'),
(6, 3, 110, '2025-06-15'),
(7, 4, 30, '2025-06-07'),
(8, 5, 110, '2025-06-06'),
(9, 6, 130, '2025-06-21'),
(10, 7, 130, '2025-06-21'),
(11, 5, 30, '2025-06-21');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `bus`
--
ALTER TABLE `bus`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `customer_receipt`
--
ALTER TABLE `customer_receipt`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `bus`
--
ALTER TABLE `bus`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `customer_receipt`
--
ALTER TABLE `customer_receipt`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
