import {
  useBank,
  useBankExchangeTransactionFind,
  useBankReviewFind,
} from "../hooks/queries";
import { useUserId } from "../hooks/lib";
import { Box, Card, CardContent, Typography } from "@mui/material";

const BankExchangeTransactionsList: React.FC<{ filter?: string }> = ({
  filter,
}) => {
  const [userId] = useUserId();

  const { data: banks = [] } = useBank();

  const { data: transactions = [] } = useBankExchangeTransactionFind({
    userId: userId as number,
  });

  return (
    <Box sx={{ overflow: "auto", display: "grid", gridTemplateColumns: "1fr 1fr 1fr" }}>
      {transactions
        .slice()
        .reverse()
        .map((transaction) => (
          <Card sx={{ m: 2, ml: "auto", mr: "auto", width: "90%", maxWidth: 500 }}>
            <CardContent>
              <Typography variant="h5">
                Банк: {banks.find(({ id }) => id === transaction.bankId)?.name}
              </Typography>
              <Typography
                sx={{ fontSize: 14 }}
                color="text.secondary"
                gutterBottom
              >
                Из {transaction.currencyIn} {transaction.amountIn}
              </Typography>
              <Typography
                sx={{ fontSize: 14 }}
                color="text.secondary"
                gutterBottom
              >
                в {transaction.currencyOut} {transaction.amountOut}
              </Typography>
            </CardContent>
          </Card>
        ))}
    </Box>
  );
};

export default BankExchangeTransactionsList;
