import { useState } from "react";
import { Box, Card, CardContent, Typography, Tab } from "@mui/material";
import TabContext from "@mui/lab/TabContext";
import TabList from "@mui/lab/TabList";
import TabPanel from "@mui/lab/TabPanel";

import { useStock, useStockPending, useStockTransactionFind } from "../hooks/queries";
import { useUserId } from "../hooks/lib";

const StockTransactionsList: React.FC<{ filter?: string }> = ({ filter }) => {
  const [userId] = useUserId();

  const [tab, setTab] = useState("1");

  const { data: stocks = [] } = useStock();
  const { data: transactions = [] } = useStockTransactionFind({
    userId: userId as number,
  });

  const { data: transactionsPending = [] } = useStockPending({
    userId: userId as number,
  });

  return (
    <Box sx={{ overflow: "auto", flex: 1 }}>
      <TabContext value={tab}>
        <TabList
          onChange={(_: any, i: string) => setTab(i)}
          sx={{ width: "fit-content", m: "auto" }}
          centered
        >
          <Tab label="Проведены" value="1" />
          <Tab label="Запланированы" value="2" />
        </TabList>
        <TabPanel value="1">
          {transactions
            .slice()
            .reverse()
            .map((transaction) => (
              <Card sx={{ m: 1, ml: "auto", mr: "auto", maxWidth: 400 }}>
                <CardContent>
                  <Typography variant="h5">
                    {transaction.goal}{" "}
                    {stocks.find(({ id }) => id === transaction.stockId)?.sign}
                  </Typography>
                  <Typography
                    sx={{ fontSize: 14 }}
                    color="text.secondary"
                    gutterBottom
                  >
                    ${transaction.stockPrice} pcs.{transaction.amount} ($
                    {(transaction.stockPrice || 1) *
                      (transaction.amount || 1)}
                    )
                  </Typography>
                </CardContent>
              </Card>
            ))}
        </TabPanel>
        <TabPanel value="2">
          {transactionsPending
            .slice()
            .reverse()
            .map((transaction) => (
              <Card sx={{ m: 1, ml: "auto", mr: "auto", maxWidth: 400 }}>
                <CardContent>
                  <Typography variant="h5">
                    {stocks.find(({ id }) => id === transaction.stockId)?.sign}
                  </Typography>
                  <Typography
                    sx={{ fontSize: 14 }}
                    color="text.secondary"
                    gutterBottom
                  >
                    ${transaction.desiredPrice} pcs.{transaction.amount} ($
                    {transaction.desiredPrice * transaction.amount})
                  </Typography>
                </CardContent>
              </Card>
            ))}
        </TabPanel>
      </TabContext>
    </Box>
  );
};

export default StockTransactionsList;
