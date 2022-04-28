import {
  useBank,
  useBankExchangeTransactionFind,
  useBankReviewFind,
} from "../queries";
import { useUserId } from "../lib";
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
    <Box sx={{ overflow: "auto", flex: 1 }}>
      {transactions
        .slice()
        .reverse()
        .map((transaction) => (
          <Card sx={{ m: 1, ml: "auto", mr: "auto", maxWidth: 400 }}>
            <CardContent>
              <Typography variant="h5">
                Bank: {banks.find(({ id }) => id === transaction.bankId)?.name}
              </Typography>
              <Typography
                sx={{ fontSize: 14 }}
                color="text.secondary"
                gutterBottom
              >
                From {transaction.currencyIn} {transaction.amountIn}
              </Typography>
              <Typography
                sx={{ fontSize: 14 }}
                color="text.secondary"
                gutterBottom
              >
                To {transaction.currencyOut} {transaction.amountOut}
              </Typography>
            </CardContent>
          </Card>
        ))}
    </Box>
  );
};

export default BankExchangeTransactionsList;