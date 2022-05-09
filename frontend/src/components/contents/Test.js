import * as React from 'react';
import PropTypes from 'prop-types';
import Box from '@mui/material/Box';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import TableSortLabel from '@mui/material/TableSortLabel';
import Paper from '@mui/material/Paper';
import { visuallyHidden } from '@mui/utils';

function createData(name, writer, story_date) {
  return {
    name,
    writer,
    story_date
  };
}

const rows = [
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
  createData('하기싫다', '작성자', '2016.21.15'),
];

function descendingComparator(a, b, orderBy) {
  if (b[orderBy] < a[orderBy]) {
    return -1;
  }
  if (b[orderBy] > a[orderBy]) {
    return 1;
  }
  return 0;
}

function getComparator(order, orderBy) {
  return order === 'desc'
    ? (a, b) => descendingComparator(a, b, orderBy)
    : (a, b) => -descendingComparator(a, b, orderBy);
}

function stableSort(array, comparator) {
  const stabilizedThis = array.map((el, index) => [el, index]);
  stabilizedThis.sort((a, b) => {
    const order = comparator(a[0], b[0]);
    if (order !== 0) {
      return order;
    }
    return a[1] - b[1];
  });
  return stabilizedThis.map((el) => el[0]);
}

const headCells = [
  {
    id: 'title_name',
    numeric: false,
    disablePadding: true,
    label: '제목',
  },
  {
    id: 'title_writer',
    numeric: true,
    disablePadding: false,
    label: '글쓴이',
  },
  {
    id: 'title_story_date',
    numeric: true,
    disablePadding: false,
    label: '작성일자',
  },
];

function EnhancedTableHead(props) {
  const { order, orderBy, onRequestSort } =
    props;
  const createSortHandler = (property) => (event) => {
    onRequestSort(event, property);
  };

  return (
    <TableHead>
      <TableRow>
        {headCells.map((headCell) => (
          <TableCell
            key={headCell.id}
            align={headCell.numeric ? 'right' : 'left'}
            padding={headCell.disablePadding ? 'none' : 'normal'}
            sortDirection={orderBy === headCell.id ? order : false}
          >
            <TableSortLabel
              active={orderBy === headCell.id}
              direction={orderBy === headCell.id ? order : 'asc'}
              onClick={createSortHandler(headCell.id)}
            >
              {headCell.label}
              {orderBy === headCell.id ? (
                <Box component="span" sx={visuallyHidden}>
                  {order === 'desc' ? 'sorted descending' : 'sorted ascending'}
                </Box>
              ) : null}
            </TableSortLabel>
          </TableCell>
        ))}
      </TableRow>
    </TableHead>
  );
}

EnhancedTableHead.propTypes = {
  onRequestSort: PropTypes.func.isRequired,
  order: PropTypes.oneOf(['asc', 'desc']).isRequired,
  orderBy: PropTypes.string.isRequired,
};

export default function EnhancedTable(props) {
  const [order, setOrder] = React.useState('asc');
  const [orderBy, setOrderBy] = React.useState('writer');
  const [selected, setSelected] = React.useState([]);
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(20);

  const handleRequestSort = (event, property) => {
    const isAsc = orderBy === property && order === 'asc';
    setOrder(isAsc ? 'desc' : 'asc');
    setOrderBy(property);
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const isSelected = (name) => selected.indexOf(name) !== -1;

  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - rows.length) : 0;


  return (
    // <Box id="div_table" sx={{ width: '100%' }}>
      <Paper id="div_table" sx={{ width: '100%' }}>
        <TableContainer>
          <Table
            sx={{ minWidth: 200 }}
            aria-labelledby="tableTitle"
          >
            <EnhancedTableHead
              order={order}
              orderBy={orderBy}
              onRequestSort={handleRequestSort}
            />
            <TableBody>
              {stableSort(props.contents, getComparator(order, orderBy))
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((row, index) => {
                  const isItemSelected = isSelected(row.name);

                  return (
                    <TableRow
                      hover
                      aria-checked={isItemSelected}
                      tabIndex={-1}
                      key={row.name}
                      selected={isItemSelected}
                    >
                      <TableCell align="left">{row.title}</TableCell>
                      <TableCell align="right">{row.memberNickname}</TableCell>
                      <TableCell align="right">{row.createdDate}</TableCell>
                    </TableRow>
                  );
                })}
              {emptyRows > 0 && (
                <TableRow
                  style={{
                    height: 53 * emptyRows,
                  }}
                >
                  <TableCell colSpan={6} />
                </TableRow>
              )}
            </TableBody>
          </Table>
        </TableContainer>
        <TablePagination
          rowsPerPageOptions={[10, 15, 20]}
          component="div"
          count={props.contents.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
          id="div_pagination"
        />
      </Paper>
    // </Box>
  );
}
