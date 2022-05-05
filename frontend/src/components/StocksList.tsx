import { useState } from "react";
import {
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  Grid,
  Modal,
  TextField,
  Typography,
  Tab,
} from "@mui/material";
import { Line } from "react-chartjs-2";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Tooltip,
  Legend,
} from 'chart.js';
import { AxiosError } from "axios";


import TabContext from "@mui/lab/TabContext";
import TabList from "@mui/lab/TabList";
import TabPanel from "@mui/lab/TabPanel";

import {
  useStock,
  useStockAccountFind,
  useStockPriceFind,
  useUserBuyStock,
  useUserBuyStockSchedule,
  useUserSellStock,
} from "../hooks/queries";
import { useUserId } from "../hooks/lib";

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Tooltip,
    Legend
);

export const options = {
  responsive: true,
  plugins: {
    legend: {
      display: false
    },
    title: {
      display: false,
    },
  },
  scales: {
    x: {
      ticks: {
        display: false,
      },
      grid: {
        display: false,
      }
    },
  }
};

type Stock = {
  id?: number;
  name?: string;
  sign?: string;
  description?: string;
  currentStockPrice?: number;
};

const formatDate = (date: Date): string =>
  `${date.getDate()}.${date.getMonth()}.${date.getFullYear()} ${date.getHours()}:${date.getMinutes()}`;

const StockModal: React.FC<{ stock: Stock; amount?: number }> = ({
  stock,
  amount = 0,
}) => {
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => {
    setOpen(false);
    setSellAmount(0);
    setBuyAmount(0);
    setBuyWhenAmount(0);
    setBuyWhenPrice(0);
    setTab("1");
  };

  const [sellAmount, setSellAmount] = useState<number | string>(0);
  const [buyAmount, setBuyAmount] = useState<number | string>(0);
  const [buyWhenAmount, setBuyWhenAmount] = useState<number | string>(0);
  const [buyWhenPrice, setBuyWhenPrice] = useState<number | string>(0);

  const [error, setError] = useState('');

  const [tab, setTab] = useState("1");
  const [infoTab, setInfoTab] = useState("1");

  const [userId] = useUserId();

  const { data: prices = [] } = useStockPriceFind({ stockId: stock.id });

  const { mutate: sell } = useUserSellStock();
  const { mutate: buy } = useUserBuyStock();
  const { mutate: buySchedule } = useUserBuyStockSchedule();

  const data = {
    labels: prices.slice(-32).map(({ createdAt }) => formatDate(new Date(createdAt))),
    datasets: [{
      labels: 'Цена',
      data: prices.slice(-32).map(({ price }) => price),
      borderColor: 'rgb(53, 162, 235)',
      backgroundColor: 'rgba(53, 162, 235, 0.5)',
      fill: true,
    }]
  }

  return (
    <>
      <Button size="small" onClick={handleOpen}>
        информация
      </Button>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography variant="h5" sx={{ textAlign: "center" }}>
            {stock.name} ({stock.sign}) ${stock.currentStockPrice}
          </Typography>
          <Box>
            <TabContext value={infoTab}>
              <TabList
                  onChange={(_: any, i: string) => {
                    setInfoTab(i);
                  }}
                  sx={{ width: "fit-content", m: "auto" }}
                  centered
              >
                <Tab label="История цен" value="1" />
                <Tab label="Описание" value="2" />
              </TabList>
              <TabPanel value="1">
                <Box sx={{ maxHeight: 300, overflow: "auto", p: 1, pr: 2 }}>
                  <Line
                      data={data}
                      options={options}
                  />
                </Box>
              </TabPanel>
              <TabPanel value="2">
                <Box sx={{ m: 1, textAlign: "center" }}>
                  <Typography sx={{ textAlign: "center" }}>Описание</Typography>
                  <Typography>{stock.description}</Typography>
                </Box>
              </TabPanel>
            </TabContext>
          </Box>
          <Box>
            <TabContext value={tab}>
              <TabList
                onChange={(_: any, i: string) => {
                  setTab(i);
                  setError('');
                }}
                sx={{ width: "fit-content", m: "auto" }}
                centered
              >
                <Tab label="Покупка" value="1" />
                <Tab label="Запланировать" value="2" />
                <Tab label="Продажа" value="3" />
              </TabList>
              <TabPanel value="1">
                <TextField
                  sx={{ mb: 2 }}
                  type="number"
                  label="Количество"
                  fullWidth
                  value={buyAmount}
                  onChange={(e) => {
                    setBuyAmount(e.target.value ? Math.abs(+e.target.value) : '')
                  }}
                />
                <Button
                  fullWidth
                  onClick={() =>
                    buy(
                      {
                        userId: userId as number,
                        stockId: stock.id as number,
                        amount: +buyAmount,
                      },
                      {
                        onSuccess: () => {
                          setBuyAmount(0);
                        },
                        onError: (error) => {
                          if (typeof (error as AxiosError).response?.data !== "string") {
                            setError('Проблемы на сервере')
                          } else {
                            setError((error as AxiosError).response?.data)
                          }
                        }
                      }
                    )
                  }
                >
                  Купить
                </Button>
              </TabPanel>
              <TabPanel value="2">
                <TextField
                  sx={{ mb: 2 }}
                  type="number"
                  placeholder="Your amount"
                  label="Количество"
                  fullWidth
                  value={buyWhenAmount}
                  onChange={(e) => {
                    setBuyWhenAmount(e.target.value ? Math.abs(+e.target.value) : '')
                  }}
                />
                <TextField
                  sx={{ mb: 2 }}
                  type="number"
                  label="Цена"
                  fullWidth
                  value={buyWhenPrice.toString()}
                  placeholder="Your price"
                  onChange={(e) => {
                    setBuyWhenPrice(e.target.value ? Number(e.target.value) : '')
                  }}
                />
                <Button
                  fullWidth
                  onClick={() =>
                    buySchedule(
                      {
                        userId: userId as number,
                        stockId: stock.id as number,
                        amount: +buyWhenAmount,
                        desiredPriceInUSD: +buyWhenPrice,
                      },
                      {
                        onSuccess: () => {
                          setBuyWhenAmount(0);
                          setBuyWhenPrice(0);
                        },
                        onError: (error) => {
                          if (typeof (error as AxiosError).response?.data !== "string") {
                            setError('Проблемы на сервере')
                          } else {
                            setError((error as AxiosError).response?.data)
                          }
                        }
                      }
                    )
                  }
                >
                  Запланированная покупка
                </Button>
              </TabPanel>
              <TabPanel value="3">
                <TextField
                  sx={{ mb: 2 }}
                  type="number"
                  label="Количество"
                  fullWidth
                  value={sellAmount}
                  onChange={(e) =>
                    setSellAmount(e.target.value ? Math.abs(+e.target.value) : '')
                  }
                />
                <Button
                  fullWidth
                  onClick={() =>
                    sell(
                      {
                        userId: userId as number,
                        stockId: stock.id as number,
                        amount: +sellAmount,
                      },
                      {
                        onSuccess: () => {
                          setSellAmount(0);
                        },
                        onError: (error) => {
                          if (typeof (error as AxiosError).response?.data !== "string") {
                            setError('Проблемы на сервере')
                          } else {
                            setError((error as AxiosError).response?.data)
                          }
                        }
                      }
                    )
                  }
                >
                  Продать
                </Button>
              </TabPanel>
            </TabContext>
            <Typography sx={{ textAlign: "center", color: "#e50707" }}>{error && error}</Typography>
          </Box>
        </Box>
      </Modal>
    </>
  );
};

const Stock: React.FC<{ stock: Stock; amount?: number }> = ({
  stock,
  amount = 0,
}) => {
  const { name, sign, currentStockPrice = 0 } = stock;

  return (
    <Card sx={{ m: 1 }}>
      <CardContent>
        <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
          {name} ${currentStockPrice}
        </Typography>
        <Typography variant="h5">{sign}</Typography>
      </CardContent>
      <CardActions>
        <StockModal stock={stock} amount={amount} />
      </CardActions>
    </Card>
  );
};

const StocksList: React.FC<{ filter?: string }> = ({ filter = "" }) => {
  const [userId] = useUserId();
  const { data: stocks = [] } = useStock();
  const { data: accountStocks = [] } = useStockAccountFind({
    userId: userId as number,
  });

  if (!stocks.length)
    return (
      <Box
        sx={{
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          width: "100%",
        }}
      >
        <Typography variant="h5" component="div">
          Рынок пуст
        </Typography>
      </Box>
    );

  return (
    <Box sx={{ width: "100%" }}>
      <Typography sx={{ m: 1, ml: 4 }}>Рынок</Typography>
      <Grid container>
        {stocks
          .filter(
            ({ name = "", sign = "" }) =>
              name.toLowerCase().includes(filter) ||
              sign.toLowerCase().includes(filter)
          )
          .slice()
          .sort(
            (s1, s2) =>
              Number(!!accountStocks.find(({ stockId }) => stockId === s2.id)) -
              Number(!!accountStocks.find(({ stockId }) => stockId === s1.id))
          )
          .map((stock) => (
            <Grid key={stock.id} item xs={3}>
              <Stock
                stock={stock}
                amount={
                  accountStocks.find(({ stockId }) => stockId === stock.id)
                    ?.amount
                }
              />
            </Grid>
          ))}
      </Grid>
    </Box>
  );
};

export default StocksList;

const style = {
  position: "absolute" as "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: "60%",
  bgcolor: "background.paper",
  borderRadius: 4,
  boxShadow: 24,
  p: 4,
};
