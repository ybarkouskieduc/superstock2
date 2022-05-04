import { useState } from "react";
import { AxiosError } from "axios";
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

  return (
    <>
      <Button size="small" onClick={handleOpen}>
        info
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
                <Tab label="Prices history" value="1" />
                <Tab label="About" value="2" />
              </TabList>
              <TabPanel value="1">
                <Box sx={{ maxHeight: 300, overflow: "auto", p: 1, pr: 2 }}>
                  <table style={{ textAlign: "center", width: "100%" }}>
                    <thead style={{ fontWeight: "bold", fontSize: "18px" }}>
                      <tr>
                        <td>Date</td>
                        <td>Price</td>
                      </tr>
                    </thead>
                    <tbody>
                      {prices.map(({ id, price, createdAt }) => (
                          <tr>
                            <td style={{ textAlign: "left" }}><Typography>{formatDate(new Date(createdAt))}</Typography></td>
                            <td><Typography>${price}</Typography></td>
                          </tr>
                      ))}
                    </tbody>
                  </table>
                </Box>
              </TabPanel>
              <TabPanel value="2">
                <Box sx={{ m: 1, textAlign: "center" }}>
                  <Typography sx={{ textAlign: "center" }}>About</Typography>
                  <Typography>{stock.description}</Typography>
                </Box>
              </TabPanel>
            </TabContext>
          </Box>
          <Box sx={{ pl: 4, pr: 4 }}>
            <TabContext value={tab}>
              <TabList
                onChange={(_: any, i: string) => {
                  setTab(i);
                  setError('');
                }}
                sx={{ width: "fit-content", m: "auto" }}
                centered
              >
                <Tab label="Buy" value="1" />
                <Tab label="Buy Schedule" value="2" />
                <Tab label="Sell" value="3" />
              </TabList>
              <TabPanel value="1">
                <TextField
                  sx={{ mb: 2 }}
                  type="number"
                  label="Amount"
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
                  Buy
                </Button>
              </TabPanel>
              <TabPanel value="2">
                <TextField
                  sx={{ mb: 2 }}
                  type="number"
                  label="Amount"
                  placeholder="Your amount"
                  fullWidth
                  value={buyWhenAmount}
                  onChange={(e) => {
                    setBuyWhenAmount(e.target.value ? Math.abs(+e.target.value) : '')
                  }}
                />
                <TextField
                  sx={{ mb: 2 }}
                  type="number"
                  label="Price"
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
                  Buy Schedule
                </Button>
              </TabPanel>
              <TabPanel value="3">
                <TextField
                  sx={{ mb: 2 }}
                  type="number"
                  label="Amount"
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
                  Sell
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
        {!!amount && (
          <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
            amount: {amount} (${amount * currentStockPrice})
          </Typography>
        )}
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
          No stocks
        </Typography>
      </Box>
    );

  return (
    <Box sx={{ width: "100%" }}>
      <Typography sx={{ m: 1, ml: 4 }}>Stocks</Typography>
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
  width: 400,
  bgcolor: "background.paper",
  borderRadius: 4,
  boxShadow: 24,
  p: 4,
};
