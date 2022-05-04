import { useState } from "react";
import useLocalStorageState from "use-local-storage-state";
import {
  Link,
  Outlet,
  Route,
  Routes,
  useLocation,
  useNavigate,
} from "react-router-dom";
import { Box, Button, TextField } from "@mui/material";
import LogoutIcon from "@mui/icons-material/Logout";

import { useUserCreate, useUserLogin } from "./queries";
import StocksList from "./components/StocksList";
import { useUserId } from "./lib";
import BankAccount from "./components/BankAccount";
import SearchInput from "./components/SearchInput";
import StockTransactionsList from "./components/StockTransactionsList";
import BankExchangeTransactionsList from "./components/BankExchangeTransactionsList";

const menu = [
  {
    icon: require("@mui/icons-material/Inventory2").default,
    path: "/",
  },
  {
    icon: require("@mui/icons-material/History").default,
    path: "/operations",
  },
  {
    icon: require("@mui/icons-material/AccountBalanceWallet").default,
    path: "/banking",
  },
];

const colors = {
  text: "#242323",
  bg: "#d6e3f8",
  focused: "#265299",
};

const LoginPage: React.FC = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const { mutate } = useUserLogin();

  const navigate = useNavigate();

  const [, setUserId] = useUserId();

  return (
    <Box
      sx={{
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        width: "100%",
      }}
    >
      <Box sx={{ display: "flex", flexDirection: "column" }}>
        <TextField
          sx={{ m: 1 }}
          label="Логин"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <TextField
          sx={{ m: 1 }}
          label="Пароль"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <Button
          sx={{ m: 1 }}
          onClick={() =>
            mutate(
              { username, password },
              {
                onSuccess: (data) => {
                  setUserId(data.id as number);
                  navigate("/");
                },
              }
            )
          }
        >
          Логин
        </Button>
        <Link to="/register" style={{ textAlign: "center" }}>
          Регистрация
        </Link>
      </Box>
    </Box>
  );
};
const RegisterPage: React.FC = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const { mutate } = useUserCreate();

  const navigate = useNavigate();
  const [, setUserId] = useUserId();

  return (
    <Box
      sx={{
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        width: "100%",
      }}
    >
      <Box sx={{ display: "flex", flexDirection: "column" }}>
        <TextField
          sx={{ m: 1 }}
          label="Логин"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <TextField sx={{ m: 1 }} label="Email" type="email" />
        <TextField
          sx={{ m: 1 }}
          label="Пароль"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <Button
          sx={{ m: 1 }}
          onClick={() =>
            mutate(
              { username, password },
              {
                onSuccess: (data) => {
                  setUserId(data.id as number);
                  navigate("/");
                },
              }
            )
          }
        >
          Register
        </Button>
        <Link to="/" style={{ textAlign: "center" }}>
          login
        </Link>
      </Box>
    </Box>
  );
};

const StocksPage: React.FC = () => {
  const [filter, setFilter] = useState("");

  return (
    <Box sx={{ flex: 1 }}>
      <Box sx={{ m: 1, display: "flex", justifyContent: "space-between" }}>
        <div />
        <SearchInput value={filter} onChange={setFilter} />
        <BankAccount />
      </Box>
      <StocksList filter={filter} />
    </Box>
  );
};
const OperationsPage: React.FC = () => {
  const [filter, setFilter] = useState("");

  return (
    <Box sx={{ display: "flex", flexDirection: "column", flex: 1 }}>
      <Box sx={{ m: 1, display: "flex", justifyContent: "space-between" }}>
        <div />
        {/*<SearchInput value={filter} onChange={setFilter} />*/}
        <BankAccount />
      </Box>
      <StockTransactionsList filter={filter} />
    </Box>
  );
};
const BankingPage: React.FC = () => {
  const [filter, setFilter] = useState("");

  return (
    <Box sx={{ display: "flex", flexDirection: "column", flex: 1 }}>
      <Box sx={{ m: 1, display: "flex", justifyContent: "space-between" }}>
        <div />
        {/*<SearchInput value={filter} onChange={setFilter} />*/}
        <BankAccount />
      </Box>
      <BankExchangeTransactionsList filter={filter} />
    </Box>
  );
};

const MainLayout: React.FC = () => {
  const { pathname } = useLocation();
  const [userId, setUserId] = useUserId();

  const navigate = useNavigate();

  if (userId === null)
    return (
      <Box sx={{ display: "flex", backgroundColor: colors.bg, height: "100%" }}>
        <Outlet />
      </Box>
    );

  return (
    <Box sx={{ display: "flex", backgroundColor: colors.bg, height: "100%" }}>
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
        }}
      >
        {menu.map((Item) => (
          <Link key={Item.path} to={Item.path}>
            <Item.icon
              sx={{
                m: 2,
                width: 40,
                height: 40,
                color: pathname === Item.path ? colors.focused : colors.text,
              }}
            />
          </Link>
        ))}
        <LogoutIcon
          onClick={() => {
            setUserId(null);
            navigate("/");
          }}
          sx={{
            m: 2,
            width: 40,
            height: 40,
            color: colors.text,
            cursor: "pointer",
          }}
        />
      </Box>
      <Outlet />
    </Box>
  );
};

const App: React.FC = () => {
  const [userId] = useUserId();

  if (userId === null)
    return (
      <Routes>
        <Route path="/" element={<MainLayout />}>
          <Route path="" element={<LoginPage />} />
          <Route path="register" element={<RegisterPage />} />
        </Route>
      </Routes>
    );

  return (
    <Routes>
      <Route path="/" element={<MainLayout />}>
        <Route path="" element={<StocksPage />} />
        <Route path="operations" element={<OperationsPage />} />
        <Route path="banking" element={<BankingPage />} />
      </Route>
    </Routes>
  );
};

export default App;
