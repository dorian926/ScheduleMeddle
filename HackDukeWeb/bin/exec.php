<?php
header('Content-disposition: attachment; filename=example.txt');
header('Content-type: txt');
readfile('example.txt');
?>